package ru.learn.skill.spring.kafka.orders.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.skill.spring.kafka.orders.service.OrderService;
import ru.learn.skill.spring.kafka.orders.web.model.request.Order;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody Order order) {
        orderService.sendMessage(order);
        return ResponseEntity.noContent().build();
    }

}
