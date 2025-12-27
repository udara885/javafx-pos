package com.udara.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.udara.pos.bo.BoFactory;
import com.udara.pos.bo.custom.CustomerBo;
import com.udara.pos.bo.custom.LoyaltyCardBo;
import com.udara.pos.bo.custom.OrderDetailBo;
import com.udara.pos.bo.custom.ProductDetailBo;
import com.udara.pos.dto.*;
import com.udara.pos.enums.BoType;
import com.udara.pos.enums.CardType;
import com.udara.pos.util.QrDataGenerator;
import com.udara.pos.util.UserSessionData;
import com.udara.pos.view.tm.CartTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PlaceOrderFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Hyperlink urlNewLoyalty;
    public Label lblLoyaltyType;
    public TextField txtBarcode;
    public TextField txtDescription;
    public TextField txtSellingPrice;
    public TextField txtDiscount;
    public TextField txtShowPrice;
    public TextField txtBuyingPrice;
    public Label LblDiscountAvailable;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colSellingPrice;
    public TableColumn colDiscount;
    public TableColumn colShowPrice;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public TableColumn colOperation;
    public Label txtTotal;

    CustomerBo bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ProductDetailBo productDetailBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAIL);
    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private LoyaltyCardBo loyaltyCardBo = BoFactory.getInstance().getBo(BoType.LOYALTY_CARD);

    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", false);
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm", true);
    }

    public void btnNewProductOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProductMainForm", true);
    }

    public void btnCompleteOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetailDto> dtoList = new ArrayList<>();
        double discount = 0;
        for (CartTm tm : tms) {
            dtoList.add(new ItemDetailDto(tm.getCode(), tm.getQty(), tm.getDiscount(), tm.getTotalCost()));
            discount += tm.getDiscount();
        }
        OrderDetailDto dto = new OrderDetailDto(orderDetailBo.getLastId() + 1, new Date(), Double.parseDouble(txtTotal.getText().split(" /=")[0]), txtEmail.getText(), discount, UserSessionData.email, dtoList);
        try {
            if (orderDetailBo.makeOrder(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!").show();
                clearProductDetails();
                clearCustomerDetails();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setUi(String url, boolean state) throws IOException {
        Stage stage = null;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml")));
        if (state) {
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            stage = (Stage) context.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customer = bo.findCustomer(txtEmail.getText());
            if (customer != null) {
                txtName.setText(customer.getName());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                txtContact.setText(customer.getContact());
                fetchLoyaltyCardData(txtEmail.getText());
            } else {
                new Alert(Alert.AlertType.WARNING, "Can't Find the Customer!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fetchLoyaltyCardData(String email) {
        urlNewLoyalty.setText("+ New Loyalty");
        urlNewLoyalty.setVisible(true);

    }

    public void newLoyaltyOnAction(ActionEvent actionEvent) {
        try {
            double salary = Double.parseDouble(txtSalary.getText());
            CardType type = null;
            if (salary >= 100000) {
                type = CardType.PLATINUM;
            } else if (salary >= 50000) {
                type = CardType.GOLD;
            } else {
                type = CardType.SILVER;
            }
            String uniqueData = QrDataGenerator.generate(25);
            //----------------- Gen QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeWriter.encode(uniqueData, BarcodeFormat.QR_CODE, 190, 190));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
            byte[] arr = baos.toByteArray();
            if (urlNewLoyalty.getText().equals("+ New Loyalty")) {
                boolean isLoyaltyCardAssigned = loyaltyCardBo.saveLoyaltyData(new LoyaltyCardDto(new Random().nextInt(100001), type, Base64.encodeBase64String(arr), txtEmail.getText()));
                if (isLoyaltyCardAssigned) {
                    new Alert(Alert.AlertType.INFORMATION, "Saved!").show();
                    urlNewLoyalty.setText("Show Loyalty Card Info");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again Shortly!").show();
                }
            } else {

            }
        } catch (ClassNotFoundException | SQLException | WriterException | IOException e) {
            new Alert(Alert.AlertType.WARNING, "Try Again Shortly!").show();
            e.printStackTrace();
        }
    }

    public void loadProductOnAction(ActionEvent actionEvent) {
        try {
            ProductDetailJoinDto productJoinDetail = productDetailBo.findProductJoinDetail(txtBarcode.getText());
            if (productJoinDetail != null) {
                txtDescription.setText(productJoinDetail.getDescription());
                txtQtyOnHand.setText(String.valueOf(productJoinDetail.getDto().getQtyOnHand()));
                txtSellingPrice.setText(String.valueOf(productJoinDetail.getDto().getSellingPrice()));
                txtDiscount.setText(String.valueOf(250));
                txtShowPrice.setText(String.valueOf(productJoinDetail.getDto().getShowPrice()));
                txtBuyingPrice.setText(String.valueOf(productJoinDetail.getDto().getBuyingPrice()));
                txtQty.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Can't Find the Product!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    ObservableList<CartTm> tms = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());
        /*if (customer.cardType.equals(CardType.GOLD.name())){

        }*/
        double discount = 250;
        double sellingPrice = Double.parseDouble(txtSellingPrice.getText()) - discount;
        double totalCost = qty * sellingPrice;
        CartTm selectedCartTm = isExists(txtBarcode.getText());
        if (selectedCartTm != null) {
            selectedCartTm.setQty(qty + selectedCartTm.getQty());
            selectedCartTm.setTotalCost(totalCost + selectedCartTm.getTotalCost());
            tblCart.refresh();
            setTotal();
        } else {
            Button btn = new Button("Remove");
            CartTm tm = new CartTm(txtBarcode.getText(), txtDescription.getText(), sellingPrice, Double.parseDouble(txtDiscount.getText()), Double.parseDouble(txtShowPrice.getText()), qty, totalCost, btn);
            btn.setOnAction((e) -> {
                tms.remove(tm);
                tblCart.refresh();
                setTotal();
            });
            tms.add(tm);
            tblCart.setItems(tms);
            setTotal();
            clearProductDetails();
        }
    }

    private CartTm isExists(String code) {
        for (CartTm tm : tms) {
            if (tm.getCode().equals(code)) {
                return tm;
            }
        }
        return null;
    }

    private void setTotal() {
        double total = 0;
        for (CartTm tm : tms) {
            total += tm.getTotalCost();
        }
        txtTotal.setText(total + " /=");
    }

    private void clearProductDetails() {
        txtBarcode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtSellingPrice.clear();
        txtDiscount.clear();
        txtShowPrice.clear();
        txtBuyingPrice.clear();
        txtQty.clear();
        txtBarcode.requestFocus();
    }

    private void clearCustomerDetails() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
    }
}
