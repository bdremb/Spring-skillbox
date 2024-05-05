package ru.learn.skill.spring.kafka.orders.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {

    private String product;
    private Integer quantity;
}
