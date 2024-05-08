package com.hanaro.starbucks.service;

import com.hanaro.starbucks.config.JwtUtil;
import com.hanaro.starbucks.dto.orders.OrderEditReqDto;
import com.hanaro.starbucks.dto.orders.OrderReqDto;
import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.entity.OrderDetail;
import com.hanaro.starbucks.entity.Orders;
import com.hanaro.starbucks.repository.OrderDetailRepository;
import com.hanaro.starbucks.repository.OrderRepository;
import com.hanaro.starbucks.util.Constant;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secretKey}")
    private String secretKey;
    public List<OrderResDto> getOrders() {
        List<Orders> orders = orderRepository.findAllByOrderByOrderIdxDesc();
        return orders.stream()
                .map(order -> {
                    List<OrderDetail> orderDetails = order.getOrderDetails();
                    int totalPrice = calculateTotalPrice(orderDetails);
                    Integer userIdx = order.getUser() != null ? order.getUser().getUserIdx() : null;
                    String userNickname = order.getUser()!=null? order.getUser().getUserNickname() : null ;
                    return OrderResDto.builder()
                            .userIdx(userIdx)
                            .userNickname(userNickname)
                            .orderIdx(order.getOrderIdx())
                            .orderId(order.getOrderId())
                            .totalPrice(totalPrice)
                            .orderStatus(order.getOrderStatus())
                            .orderDate(order.getOrderDate())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public OrderResDto getOrder(int orderIdx) {
        Orders order = findOrderById(orderIdx);
        Integer userIdx = order.getUser() != null ? order.getUser().getUserIdx() : null;
        String userNickname = order.getUser()!=null? order.getUser().getUserNickname() : null ;
        return OrderResDto.builder()
                .userIdx(userIdx)
                .userNickname(userNickname)
                .orderIdx(order.getOrderIdx())
                .orderId(order.getOrderId())
                .totalPrice(calculateTotalPrice(order.getOrderDetails()))
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .build();
    }

    public void updateOrder(int orderIdx, OrderEditReqDto orderEditReqDto) {
        Orders order = findOrderById(orderIdx);
        order.updateOrderStatus(orderEditReqDto.getOrderStatus());
        orderRepository.save(order);
    }

    public void deleteOrder(int orderIdx){
        Orders order = findOrderById(orderIdx);
        order.getOrderDetails().forEach(orderDetailRepository::delete);
        orderRepository.deleteById(orderIdx);
    }
    private Orders findOrderById(int orderIdx) {
        Optional<Orders> optionalOrders = orderRepository.findById(orderIdx);
        return optionalOrders.orElseThrow(() -> new IllegalArgumentException("주문 내역이 존재하지 않습니다."));
    }

    public void createOrder(String token, List<OrderReqDto> dtos){

        String userId = jwtUtil.getAuthentication(token).getName();
        System.out.println("User ID: " + userId);

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
