package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "order_detail")
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_idx")
    private int orderDetailIdx;

    @ManyToOne
    @JoinColumn(name = "order_idx", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "menu_idx", nullable = false)
    private Menu menu;

    @ColumnDefault("0")
    @Column(name = "order_detail_count")
    private int orderDetailCount;

    @Column(name = "menu_temperature", nullable = false)
    private String menuTemperature;

    @Column(name = "menu_size", nullable = false)
    private String menuSize;

    @Builder
    public OrderDetail(Orders orders, Menu menu, int orderDetailCount, String menuTemperature, String menuSize) {
        this.orders = orders;
        this.menu = menu;
        this.orderDetailCount = orderDetailCount;
        this.menuTemperature = menuTemperature;
        this.menuSize = menuSize;
    }
}
