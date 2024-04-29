package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.menu.MenuResDto;
import com.hanaro.starbucks.entity.Menu;
import com.hanaro.starbucks.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuResDto> getMenuList() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(MenuResDto::new).collect(Collectors.toList());
    }
}
