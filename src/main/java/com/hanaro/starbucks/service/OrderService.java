package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.entity.OrderDetail;
import com.hanaro.starbucks.entity.Orders;
import com.hanaro.starbucks.repository.OrderRepository;
import com.hanaro.starbucks.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderResDto> getOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderResDto> orderResDtos = orders.stream()
                .map(order -> {
                    List<OrderDetail> orderDetails = order.getOrderDetails();
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

    public OrderResDto getOrder(int orderIdx) {
        Optional<Orders> optionalOrders = orderRepository.findById(orderIdx);
        if(optionalOrders.isEmpty()){
            throw new IllegalArgumentException("주문 내역이 존재하지 않습니다.");
        }
        Orders order = optionalOrders.get();
        return OrderResDto.builder()
                .orderIdx(order.getOrderIdx())
                .orderId(order.getOrderId())
                .totalPrice(calculateTotalPrice(order.getOrderDetails()))
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .build();
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
