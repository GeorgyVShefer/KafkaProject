package org.example.authmodule.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(topics = "verification-requested", partitions = 1)
@SpringBootTest
class KafkaIntegrationTest {

    @Autowired
    private VerificationProducer producer;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Test
    void testKafkaSend() throws Exception {

        producer.send("user@example.com", "123456");
    }
}