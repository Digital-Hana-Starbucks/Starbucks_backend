package com.hanaro.starbucks.entity;

import com.hanaro.starbucks.dto.menu.MenuReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;


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
    private LocalDate menuDate;

    @ManyToOne
    @JoinColumn(name = "category_idx")
    private Category category;


    public void update(MenuReqDto dto, Category category, String img) {
        this.menuName = dto.getMenuName();
        this.menuPrice = dto.getMenuPrice();
        this.menuImage = img;
        this.category = category;
        this.menuDate = LocalDate.now();
    }

}