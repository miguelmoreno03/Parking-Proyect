package com.bit.solutions.parking_system.notification.QR;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeServiceImpl implements QrCodeService {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    @Override
    public byte[] generateQrCode(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("QR content cannot be null or blank");
        }

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    WIDTH,
                    HEIGHT
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return outputStream.toByteArray();

        } catch (WriterException | IOException e) {
            throw new IllegalStateException("Error generating QR code", e);
        }
    }
}
