package com.exadel.discountwebapp.notification;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class NotificationMailSender {

    private final JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendMail(String subject, String content, String[] to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setTo(to);

        javaMailSender.send(message);
    }
}
