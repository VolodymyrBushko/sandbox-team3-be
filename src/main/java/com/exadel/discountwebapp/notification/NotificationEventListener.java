package com.exadel.discountwebapp.notification;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.notification.event.EntityCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationMailSender mailSender;
    private final MailProvider mailProvider;

    @Async
    @EventListener(value = {EntityCreateEvent.class})
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendNotification(EntityCreateEvent<Discount> event) {
        Mail mail = mailProvider.getMail(event.getEntity());
        mailSender.sendMail(mail);
    }
}
