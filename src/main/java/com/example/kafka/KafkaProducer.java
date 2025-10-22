package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${kafka.topics.test-topic}")
    private String topic;

    private final KafkaTemplate<String, MessageObject> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, MessageObject> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(String nameMethod, String emailOfUser) {
        MessageObject message = createMessage(nameMethod, emailOfUser);

        CompletableFuture<SendResult<String, MessageObject>> futureResultSending
                = kafkaTemplate.send(topic, message);

        futureResultSending
                .thenAccept((sendResult) -> {
                    logger.debug("Message sent successfully from method: {}, mailOfUser: {} in topic: {}", nameMethod, emailOfUser, sendResult.getRecordMetadata().topic());
                })
                .exceptionally(exception -> {
                    logger.error("Message not sent! Method: {}, mailOfUser: {}, error: {}",
                            nameMethod, emailOfUser, exception.getMessage());
                    return null;
                });

        return true;
    }

    private MessageObject createMessage(String nameMethod, String emailOfUser) {
        return new MessageObject(nameMethod, emailOfUser);
    }
}

