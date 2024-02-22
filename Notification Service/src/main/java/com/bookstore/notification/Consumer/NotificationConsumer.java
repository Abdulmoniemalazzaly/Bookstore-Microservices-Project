package com.bookstore.notification.Consumer;

import com.bookstore.commons.record.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class NotificationConsumer {

    private final JavaMailSender emailSender;
    private final String MAIL_FROM;
    private final String MAIL_SENDER_NAME;
    private final String SUBJECT;
    private final String CONTENT;

    public NotificationConsumer(JavaMailSender emailSender,
                                @Value("${mail.from}") String mailFrom,
                                @Value("${mail.sender-name}") String mailSenderName,
                                @Value("${mail.purchase-book.subject}") String subject,
                                @Value("${mail.purchase-book.content}") String content) {
        this.emailSender = emailSender;
        MAIL_FROM = mailFrom;
        MAIL_SENDER_NAME = mailSenderName;
        SUBJECT = subject;
        CONTENT = content;
    }

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consume(PaymentMessage message) throws MessagingException, UnsupportedEncodingException {
        log.info("Message consumed {}" , message);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(MAIL_FROM , MAIL_SENDER_NAME);
        helper.setTo(message.userEmail());
        helper.setSubject(SUBJECT);
        String content = CONTENT
                .replace("[[payment-id]]" , message.paymentId())
                .replace("[[code]]" , message.code());
        helper.setText(content , true);
        emailSender.send(mimeMessage);
        log.info("Message sent - payment with id {} " + message.paymentId());
    }
}
