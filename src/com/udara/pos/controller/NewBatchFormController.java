package com.udara.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.udara.pos.bo.BoFactory;
import com.udara.pos.bo.custom.ProductDetailBo;
import com.udara.pos.dto.ProductDetailDto;
import com.udara.pos.enums.BoType;
import com.udara.pos.util.QrDataGenerator;
import com.udara.pos.view.tm.ProductDetailTm;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class NewBatchFormController {
    public ImageView imgBarcode;
    public TextField txtQty;
    public TextField txtBuyingPrice;
    public TextField txtSellingPrice;
    public TextField txtShowPrice;
    public TextField txtProductCode;
    public TextField txtProductDescription;
    public RadioButton rBtnYes;
    public ToggleGroup discount;

    String uniqueData = null;
    BufferedImage bufferedImage = null;
    Stage stage = null;
    private ProductDetailBo productDetailBo = BoFactory.getInstance().getBo(BoType.PRODUCT_DETAIL);

    private void setQRCode() throws WriterException {
        uniqueData = QrDataGenerator.generate(25);
        //----------------- Gen QR
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        bufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeWriter.encode(uniqueData, BarcodeFormat.QR_CODE, 190, 190));
        //----------------- Gen QR
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imgBarcode.setImage(image);
    }

    public void setDetails(int code, String description, Stage stage, boolean state, ProductDetailTm tm) {
        this.stage = stage;
        if (state) {
            try {
                ProductDetailDto productDetail = productDetailBo.findProductDetail(tm.getCode());
                if (productDetail != null) {
                    txtQty.setText(String.valueOf(productDetail.getQtyOnHand()));
                    txtBuyingPrice.setText(String.valueOf(productDetail.getBuyingPrice()));
                    txtSellingPrice.setText(String.valueOf(productDetail.getSellingPrice()));
                    txtShowPrice.setText(String.valueOf(productDetail.getShowPrice()));
                    rBtnYes.setSelected(productDetail.isDiscountAvailability());
                } else {
                    stage.close();
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                setQRCode();
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        }
        txtProductCode.setText(String.valueOf(code));
        txtProductDescription.setText(description);
    }

    public void btnSaveBatchOnAction(ActionEvent actionEvent) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(bufferedImage, "png", baos);
        byte[] arr = baos.toByteArray();
        ProductDetailDto dto = new ProductDetailDto(uniqueData, Base64.encodeBase64String(arr), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtBuyingPrice.getText()), Double.parseDouble(txtShowPrice.getText()), Integer.parseInt(txtProductCode.getText()), rBtnYes.isSelected());
        try {
            if (productDetailBo.saveProductDetail(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Batch Saved!").show();
                /*Thread.sleep(3000);
                this.stage.close();*/
                Platform.runLater(() -> {
                    this.stage.close();
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
