package ru.learn.skill.spring.kafka.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.learn.skill.spring.kafka.orders.model.OrderStatusEvent;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    @Value("${app.kafka.orderStatusTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public void sendMessage(String status) {
        OrderStatusEvent message = OrderStatusEvent.builder()
                .status(status)
                .date(Instant.now())
                .build();

        kafkaTemplate.send(topicName, message);
        log.info("Message {} sent to topic {}", message, topicName);
    }

}
