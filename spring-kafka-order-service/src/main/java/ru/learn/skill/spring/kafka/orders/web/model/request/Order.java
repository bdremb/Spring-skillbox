package ru.learn.skill.spring.kafka.orders.web.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private String product;
    private Integer quantity;
}
