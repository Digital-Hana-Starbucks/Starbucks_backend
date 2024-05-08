package com.hanaro.starbucks.service;

import com.hanaro.starbucks.config.JwtUtil;
import com.hanaro.starbucks.dto.member.MemberResDto;
import com.hanaro.starbucks.dto.orders.OrderEditReqDto;
import com.hanaro.starbucks.dto.orders.OrderReqDto;
import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.entity.Member;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MemberService memberService;
    private final MenuService menuService;
    private final JwtUtil jwtUtil;

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

    @Transactional
    public void createOrder(String token, List<OrderReqDto> dtos){
        Member member;
        Orders order;
        if(token.equals("null")){
            System.out.println("~~~~~~~~~~~~~~~~~~``");
            order = Orders.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderStatus("주문완료")
                    .build();
        }else{
            String userId = jwtUtil.getAuthentication(token).getName();
            System.out.println("User ID: " + userId);
            member = memberService.getUserById(userId);
            order = Orders.builder()
                    .user(member)
                    .orderId(UUID.randomUUID().toString())
                    .orderStatus("주문완료")
                    .build();
        }
        Orders savedOrder = orderRepository.save(order);
        System.out.println("~~~"+order.getOrderIdx());

        List<OrderDetail> orderDetails = dtos.stream()
                .map(dto -> {
                    try {
                        return OrderDetail.builder()
                                .orders(savedOrder)
                                .menu(menuService.findMenuByMenuIdx(dto.getMenuIdx()))
                                .menuSize(dto.getMenuSize())
                                .orderDetailCount(dto.getOrderDetailCount())
                                .menuTemperature(dto.getMenuTemperature())
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })
                .collect(Collectors.toList());
        System.out.println("~~~~~~~~"+orderDetails.size());
        for(OrderDetail orderDetail : orderDetails){
            System.out.println(orderDetail.getOrders());
            System.out.println(orderDetail.getMenu());
            System.out.println(orderDetail.getMenuTemperature());
            System.out.println(orderDetail.getOrderDetailIdx());
        }

        orderDetailRepository.saveAll(orderDetails);
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
