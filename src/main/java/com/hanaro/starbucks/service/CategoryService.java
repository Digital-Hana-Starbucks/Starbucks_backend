package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.category.CategoryResDto;
import com.hanaro.starbucks.entity.Category;
import com.hanaro.starbucks.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResDto::new).collect(Collectors.toList());
    }
}
