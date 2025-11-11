package org.example.emailmodule.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @KafkaListener(topics = "verification-requested", groupId = "mailer-group")
    public void listen(String message) {

        System.out.println("Received verification code: " + message);
    }
}
