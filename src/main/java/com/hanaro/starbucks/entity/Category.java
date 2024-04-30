package com.hanaro.starbucks.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx")
    private int categoryIdx;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

}
