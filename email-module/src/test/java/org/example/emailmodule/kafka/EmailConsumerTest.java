package org.example.emailmodule.kafka;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailConsumerTest {

    @Autowired
    private EmailConsumer consumer;

    @Test
    void testConsumerRuns() {

        consumer.listen("user@example.com:CODE123");
        Assertions.assertTrue(true);
    }
}