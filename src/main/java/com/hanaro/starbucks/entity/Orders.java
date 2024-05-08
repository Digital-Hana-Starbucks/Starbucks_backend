package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_idx")
    private int orderIdx;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = true)
    private Member user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public void updateOrderStatus(String orderStatus){
        this.orderStatus=orderStatus;
    }

    @Builder
    public Orders(String orderId, String orderStatus, Member user) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = LocalDateTime.now();
        this.user = user;
    }
}