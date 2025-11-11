package org.example.authmodule.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class VerificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.verification-topic}")
    private String topic;

    public VerificationProducer(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String email, String code) {

        kafkaTemplate.send(topic, email, email + ":" + code);
    }
}
