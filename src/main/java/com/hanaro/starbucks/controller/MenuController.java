package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.menu.MenuResDto;
import com.hanaro.starbucks.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("")
    public List<MenuResDto> getMenuList() {
        return menuService.getMenuList();
    }
}
