package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.menu.MenuResDto;
import com.hanaro.starbucks.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/category/{categoryIdx}")
    public List<MenuResDto> getMenuListByCategoryIdx(@PathVariable int categoryIdx) throws Exception{
        return menuService.getMenuListByCategoryIdx(categoryIdx);
    }

    @GetMapping("/{menuIdx}")
    public MenuResDto getMenuByMenuIdx(@PathVariable int menuIdx) throws Exception{
        return menuService.getMenuByMenuIdx(menuIdx);
    }

    @DeleteMapping("/{menuIdx}")
    public void deleteMenuByMenuIdx(@PathVariable int menuIdx) throws Exception{
        menuService.deleteMenuByMenuIdx(menuIdx);
    }
}
