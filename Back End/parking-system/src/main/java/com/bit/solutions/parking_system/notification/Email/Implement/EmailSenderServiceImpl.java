package com.bit.solutions.parking_system.notification.Email.Implement;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import com.bit.solutions.parking_system.notification.Email.Interfaces.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import org.springframework.core.io.ByteArrayResource;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    @Override
    public void sendParkingTicketEmail(String to, String subject, String htmlContent, byte[] qrCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            helper.addInline(
                    "qrCodeImage",
                    new ByteArrayResource(qrCode),
                    "image/png"
            );

            helper.addInline(
                    "parkingLogo",
                    new ClassPathResource("static/images/ParkingProject.png"),
                    "image/png"
            );

            mailSender.send(message);

        } catch (MessagingException | MailException e) {
            throw new IllegalStateException("Error sending parking ticket email", e);
        }
    }
}
