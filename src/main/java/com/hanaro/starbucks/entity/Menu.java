package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_idx")
    private int menuIdx;

    @Column(name = "menu_id", nullable = false)
    private String menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    @Column(name = "menu_image")
    private String menuImage;

    @Column(name = "menu_date")
    private LocalDateTime menuDate;

    @ManyToOne
    @JoinColumn(name = "category_idx")
    private Category category;
}