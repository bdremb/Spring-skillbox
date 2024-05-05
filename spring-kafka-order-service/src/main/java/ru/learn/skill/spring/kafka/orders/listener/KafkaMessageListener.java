package ru.learn.skill.spring.kafka.orders.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.learn.skill.spring.kafka.orders.model.OrderEvent;
import ru.learn.skill.spring.kafka.orders.model.OrderStatusEvent;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    @KafkaListener(
            topics = "${app.kafka.orderStatusTopic}",
            groupId = "${app.kafka.orderGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listen(
            @Payload OrderStatusEvent message,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        log.info("Received message: {}", message);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }

}
