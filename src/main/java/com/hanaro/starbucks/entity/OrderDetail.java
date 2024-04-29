package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "order_detail")
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

}
