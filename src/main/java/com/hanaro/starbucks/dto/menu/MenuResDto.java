package com.hanaro.starbucks.dto.menu;

import com.hanaro.starbucks.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MenuResDto {
    private int menuIdx;

    private String menuId;

    private String menuName;

    private int menuPrice;

    private String menuImage;

    private LocalDateTime menuDate;

    private int category_idx;

    public MenuResDto(Menu menu) {
        this.menuIdx = menu.getMenuIdx();
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuImage = menu.getMenuImage();
        this.menuDate = menu.getMenuDate();
        this.category_idx = menu.getCategory().getCategoryIdx();
    }
}
