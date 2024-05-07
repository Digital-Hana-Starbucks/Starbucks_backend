package com.hanaro.starbucks.dto.category;

import com.hanaro.starbucks.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CategoryResDto {
    private int categoryIdx;

    private String categoryName;

    public CategoryResDto(Category category) {
        this.categoryIdx = category.getCategoryIdx();
        this.categoryName = category.getCategoryName();
    }
}
