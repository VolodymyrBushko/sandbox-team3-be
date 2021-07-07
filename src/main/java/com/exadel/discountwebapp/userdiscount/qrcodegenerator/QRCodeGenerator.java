package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class QRCodeGenerator {
    private final String DEFAULT_QRCODE_FORMAT = "PNG";

    @SneakyThrows
    public byte[] generateQRCodeImage(String data, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_QRCODE_FORMAT, pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
}