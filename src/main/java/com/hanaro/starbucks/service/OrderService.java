package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.entity.OrderDetail;
import com.hanaro.starbucks.entity.Orders;
import com.hanaro.starbucks.repository.OrderDetailRepository;
import com.hanaro.starbucks.repository.OrderRepository;
import com.hanaro.starbucks.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public List<OrderResDto> getOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderResDto> orderResDtos = orders.stream()
                .map(order -> {
                    List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrders(order);
                    int totalPrice = calculateTotalPrice(orderDetails);
                    return OrderResDto.builder()
                            .orderIdx(order.getOrderIdx())
                            .orderId(order.getOrderId())
                            .totalPrice(totalPrice)
                            .orderStatus(order.getOrderStatus())
                            .orderDate(order.getOrderDate())
                            .build();
                })
                .collect(Collectors.toList());
        return orderResDtos;
    }

    private int calculateTotalPrice(List<OrderDetail> orderDetails) {
        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            int menuPrice = orderDetail.getMenu().getMenuPrice();
            if (orderDetail.getMenuSize().equals(Constant.GRANDE_SIZE)) {
                menuPrice += Constant.GRANDE;
            } else if (orderDetail.getMenuSize().equals(Constant.VENTI_SIZE)) {
                menuPrice += Constant.VENTI;
            }
            totalPrice += menuPrice * orderDetail.getOrderDetailCount();
        }
        return totalPrice;
    }
}
