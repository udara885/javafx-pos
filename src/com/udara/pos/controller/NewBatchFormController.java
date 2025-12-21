package com.udara.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.udara.pos.util.QrDataGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

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

    public void initialize() throws WriterException {
        setQRCode();
    }

    private void setQRCode() throws WriterException {
        uniqueData = QrDataGenerator.generate(25);
        //----------------- Gen QR
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeWriter.encode(uniqueData, BarcodeFormat.QR_CODE, 190, 190));
        //----------------- Gen QR
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imgBarcode.setImage(image);
    }

    public void setProductCode(int code, String description) {
        txtProductCode.setText(String.valueOf(code));
        txtProductDescription.setText(description);
    }

    public void btnSaveBatchOnAction(ActionEvent actionEvent) {
    }
}
