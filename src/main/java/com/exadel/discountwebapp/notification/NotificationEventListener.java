package com.exadel.discountwebapp.notification;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.notification.event.EntityCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.mail.MessagingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private static final String SEND_MAIL_EXCEPTION_MESSAGE_PATTERN = "Failed to send the mail to: %s. Exception message: %s";

    private final NotificationMailSender mailSender;
    private final MailProvider mailProvider;

    @Async
    @EventListener(value = {EntityCreateEvent.class})
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendNotification(EntityCreateEvent<Discount> event) {
        mailProvider.getMails(event.getEntity())
                .forEach(mail -> {
                    try {
                        mailSender.sendMail(mail);
                    } catch (MessagingException ex) {
                        log.error(String.format(SEND_MAIL_EXCEPTION_MESSAGE_PATTERN, mail.getTo(), ex.getMessage()), ex);
                    }
                });
    }
}
