package com.exadel.discountwebapp.notification;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class NotificationMailSender {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @SneakyThrows
    public void sendMail(Mail mail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(mail.getVariables());

        String content = templateEngine.process(mail.getTemplate(), context);

        helper.setSubject(mail.getSubject());
        helper.setText(content, true);
        helper.setTo(mail.getTo());

        javaMailSender.send(message);
    }
}
