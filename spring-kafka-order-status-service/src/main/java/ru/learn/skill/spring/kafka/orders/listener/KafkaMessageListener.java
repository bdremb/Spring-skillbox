package ru.learn.skill.spring.kafka.orders.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.learn.skill.spring.kafka.orders.model.OrderEvent;
import ru.learn.skill.spring.kafka.orders.service.StatusService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StatusService service;

    @KafkaListener(
            topics = "${app.kafka.orderTopic}",
            groupId = "${app.kafka.orderGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory"
    )
    public void listen(@Payload OrderEvent message) {
        if (message.getProduct().isEmpty()) {
            service.sendMessage("PROCESS");
        } else {
            service.sendMessage("CREATED");
        }
    }

}
