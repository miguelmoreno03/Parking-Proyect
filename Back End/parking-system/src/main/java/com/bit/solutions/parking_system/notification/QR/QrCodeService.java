package com.bit.solutions.parking_system.notification.QR;

public interface QrCodeService {
    byte[] generateQrCode(String content);
}
