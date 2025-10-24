package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableKafka
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
class KafkaProducerIntegrationTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private org.apache.kafka.clients.consumer.Consumer<String, MessageObject> consumer;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers",
                () -> "localhost:9092");
        registry.add("kafka.topics.test-topic", () -> "test-topic");
    }

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps =
                KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);

        consumer = new DefaultKafkaConsumerFactory<>(
                consumerProps,
                new StringDeserializer(),
                new org.springframework.kafka.support.serializer.JsonDeserializer<>(MessageObject.class)
        ).createConsumer();

        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);

        for (MessageListenerContainer container : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @Test
    void testSendMessage() {
        kafkaProducer.sendMessage("testMethod", "test@example.com");

        ConsumerRecord<String, MessageObject> record =
                KafkaTestUtils.getSingleRecord(consumer, "test-topic");

        assertThat(record).isNotNull();
        assertThat(record.value().getNameMethod()).isEqualTo("testMethod");
        assertThat(record.value().getUserEmail()).isEqualTo("test@example.com");
    }
}
