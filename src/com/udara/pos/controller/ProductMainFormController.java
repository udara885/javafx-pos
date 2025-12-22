package com.udara.pos.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.udara.pos.bo.BoFactory;
import com.udara.pos.bo.custom.ProductBo;
import com.udara.pos.bo.custom.ProductDetailBo;
import com.udara.pos.dto.ProductDetailDto;
import com.udara.pos.dto.ProductDto;
import com.udara.pos.enums.BoType;
import com.udara.pos.view.tm.ProductDetailTm;
import com.udara.pos.view.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ProductMainFormController {
    public AnchorPane context;
    public JFXTextField txtProductCode;
    public TextArea txtProductDescription;
    public TextField txtSearch;
    public TableView<ProductTm> tblProduct;
    public TableColumn colProductId;
    public TableColumn colDescription;
    public TableColumn colShowMore;
    public TableColumn colProductDelete;
    public TableView<ProductDetailTm> tblBatch;
    public TableColumn colBatchId;
    public TableColumn colQty;
    public TableColumn colSellingPrice;
    public TableColumn colBuyingPrice;
    public TableColumn colDiscountAvailability;
    public TableColumn colShowPrice;
    public TableColumn colBatchDelete;
    public TextField txtSelectedProductCode;
    public TextArea txtSelectedProductDescription;
    public JFXButton btnSaveProduct;
    public JFXButton btnNewBatch;

    private String searchText = "";

    ProductBo bo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    ProductDetailBo detailBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAIL);

    public void initialize() throws SQLException, ClassNotFoundException {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colShowMore.setCellValueFactory(new PropertyValueFactory<>("showMore"));
        colProductDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colDiscountAvailability.setCellValueFactory(new PropertyValueFactory<>("discountAvailability"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colBatchDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        // load new product code
        setProductCode();
        loadAllProducts(searchText);

        tblProduct.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        }));

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchText = newValue;
            try {
                loadAllProducts(searchText);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }));

        tblBatch.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    loadExternalUi(true, newValue);
                } catch (IOException | SQLException | ClassNotFoundException | WriterException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }

    private void setData(ProductTm productTm) {
        btnSaveProduct.setText("Update Product");
        txtProductCode.setText(String.valueOf(productTm.getCode()));
        txtProductDescription.setText(productTm.getDescription());
        txtSelectedProductCode.setText(String.valueOf(productTm.getCode()));
        txtSelectedProductDescription.setText(productTm.getDescription());
        btnNewBatch.setDisable(false);
        try {
            loadBatchData(productTm.getCode());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllProducts(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<ProductTm> observableList = FXCollections.observableArrayList();
        for (ProductDto dto : !searchText.isEmpty() ? bo.searchProducts(searchText) : bo.findAllProducts()) {
            Button showMore = new Button("Show More");
            Button deleteButton = new Button("Delete");
            ProductTm tm = new ProductTm(dto.getId(), dto.getDescription(), showMore, deleteButton);
            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        if (bo.deleteProduct(dto.getId())) {
                            new Alert(Alert.AlertType.INFORMATION, "Product Deleted!").show();
                            loadAllProducts(searchText);
                            setProductCode();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }
                }
            });
            observableList.add(tm);
        }
        tblProduct.setItems(observableList);
    }

    private void loadBatchData(int code) throws SQLException, ClassNotFoundException {
        ObservableList<ProductDetailTm> observableList = FXCollections.observableArrayList();
        for (ProductDetailDto dto : detailBo.findAllProductDetails(code)) {
            Button deleteBtn = new Button("Delete");
            ProductDetailTm tm = new ProductDetailTm(dto.getCode(), dto.getQtyOnHand(), dto.getSellingPrice(), dto.getBuyingPrice(), dto.isDiscountAvailability(), dto.getShowPrice(), deleteBtn);
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        if (detailBo.deleteProductDetail(dto.getCode())) {
                            new Alert(Alert.AlertType.INFORMATION, "Batch Deleted!").show();
                            loadBatchData(dto.getProductCode());
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }
                }
            });
            observableList.add(tm);
        }
        tblBatch.setItems(observableList);
    }

    private void setProductCode() {
        try {
            txtProductCode.setText(String.valueOf(bo.getLastId() + 1));
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void btnNewProductOnAction(ActionEvent actionEvent) {
        setProductCode();
        clearFields();
        btnSaveProduct.setText("Save Product");
    }

    public void btnSaveProductOnAction(ActionEvent actionEvent) {
        try {
            if (btnSaveProduct.getText().equals("Save Product")) {
                if (bo.saveProduct(new ProductDto(Integer.parseInt(txtProductCode.getText()), txtProductDescription.getText()))) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Saved!").show();
                    clearFields();
                    setProductCode();
                    loadAllProducts(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } else {
                if (bo.updateProduct(new ProductDto(Integer.parseInt(txtProductCode.getText()), txtProductDescription.getText()))) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Updated!").show();
                    clearFields();
                    setProductCode();
                    loadAllProducts(searchText);
                    btnSaveProduct.setText("Save Product");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnNewBatchOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, WriterException {
        loadExternalUi(false, null);
    }

    private void loadExternalUi(boolean state, ProductDetailTm tm) throws IOException, SQLException, ClassNotFoundException, WriterException {
        if (!txtSelectedProductCode.getText().isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/NewBatchForm.fxml"));
            Parent parent = fxmlLoader.load();
            NewBatchFormController controller = fxmlLoader.getController();
            controller.setDetails(Integer.parseInt(txtSelectedProductCode.getText()),
                    txtSelectedProductDescription.getText(), stage, state, tm);
            stage.setScene(new Scene(parent));
            stage.show();
            stage.centerOnScreen();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a valid one!").show();
        }
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
        stage.centerOnScreen();
    }

    private void clearFields() {
        txtProductDescription.clear();
    }
}
