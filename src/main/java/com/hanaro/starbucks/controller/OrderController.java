package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.orders.OrderEditReqDto;
import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public List<OrderResDto> getOrders() {
        return orderService.getOrders();
    }
    @GetMapping("/{orderIdx}")
    public OrderResDto getOrder(@PathVariable int orderIdx) {
        return orderService.getOrder(orderIdx);
    }

    @PutMapping("/{orderIdx}")
    public void updateOrder(@PathVariable int orderIdx, @RequestBody OrderEditReqDto orderEditReqDto) {
        orderService.updateOrder(orderIdx, orderEditReqDto);
    }

    @DeleteMapping("/{orderIdx}")
    public void deleteOrder(@PathVariable int orderIdx) {
        orderService.deleteOrder(orderIdx);
    }

}
