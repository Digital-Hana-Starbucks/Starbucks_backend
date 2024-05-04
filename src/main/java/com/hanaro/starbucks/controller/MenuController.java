package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.menu.MenuReqDto;
import com.hanaro.starbucks.dto.menu.MenuResDto;
import com.hanaro.starbucks.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.hanaro.starbucks.util.APIConstant.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/products")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
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

    @DeleteMapping("/admin/{menuIdx}")
    public void deleteMenuByMenuIdx(@PathVariable int menuIdx) throws Exception{
        menuService.deleteMenuByMenuIdx(menuIdx);
    }

    @PutMapping(value = "/admin/{menuIdx}", consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
    public void updateMenu(@PathVariable int menuIdx, @RequestPart(value = "dto") MenuReqDto menuReqDto, @RequestPart(value = "menuImg") MultipartFile img) throws Exception{
        menuService.updateMenu(menuIdx, menuReqDto, img);
    }
}
