package ru.learn.skill.spring.kafka.orders.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class OrderEvent {

    private String product;
    private Integer quantity;
}
