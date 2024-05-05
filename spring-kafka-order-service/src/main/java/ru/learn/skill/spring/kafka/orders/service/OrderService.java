package ru.learn.skill.spring.kafka.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.learn.skill.spring.kafka.orders.model.OrderEvent;
import ru.learn.skill.spring.kafka.orders.web.model.request.Order;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${app.kafka.orderTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    public void sendMessage(Order order) {
        OrderEvent message = OrderEvent.builder()
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .build();

        kafkaTemplate.send(topicName, message);
        log.info("Message {} sent to topic {}", message, topicName);
    }

}

