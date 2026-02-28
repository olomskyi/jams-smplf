package com.olomsky.notifications.service;

import com.olomsky.notifications.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-topic")
    public void listen(OrderEvent orderEvent) {
        log.info("Notification Listen: order event = {}", orderEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("jams-smplf@email.com");
            messageHelper.setTo(orderEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully",
                                     orderEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                            Hi %s %s

                            Your order with order number %s is now placed successfully.
                            
                            Best Regards
                            Jams Smplf
                            """,
                    orderEvent.getFirstName(),
                    orderEvent.getLastName(),
                    orderEvent.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notifcation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to " + orderEvent.getEmail(), e);
        }
    }

}
