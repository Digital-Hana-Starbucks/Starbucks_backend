package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public List<OrderResDto> getOrders() {
        return orderService.getOrders();
    }


}
