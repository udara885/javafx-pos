package com.udara.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.udara.pos.util.QrDataGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

public class NewBatchFormController {
    public ImageView imgBarcode;

    public void initialize() throws WriterException {
        setQRCode();
    }

    private void setQRCode() throws WriterException {
        String uniqueData = QrDataGenerator.generate(25);
        //----------------- Gen QR
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeWriter.encode(uniqueData,
                BarcodeFormat.QR_CODE, 190, 190));
        //----------------- Gen QR
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imgBarcode.setImage(image);
    }
}
