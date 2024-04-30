package com.hanaro.starbucks.service;

import com.hanaro.starbucks.dto.menu.MenuReqDto;
import com.hanaro.starbucks.dto.menu.MenuResDto;
import com.hanaro.starbucks.entity.Category;
import com.hanaro.starbucks.entity.Menu;
import com.hanaro.starbucks.repository.CategoryRepository;
import com.hanaro.starbucks.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final S3Uploader s3Uploader;

    public List<MenuResDto> getMenuList() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(MenuResDto::new).collect(Collectors.toList());
    }
    public List<MenuResDto> getMenuListByCategoryIdx(int categoryIdx) throws Exception{
        Optional<Category> category = categoryRepository.findById(categoryIdx);
        if(category.isEmpty()) {
            throw new Exception("존재하지 않는 카테고리입니다.");
        }
        List<Menu> menus = menuRepository.findAllByCategory(category.get());
        return menus.stream().map(MenuResDto::new).collect(Collectors.toList());
    }
    public MenuResDto getMenuByMenuIdx(int menuIdx) throws Exception{
        Optional<Menu> optionalMenu = menuRepository.findById(menuIdx);
        if(optionalMenu.isEmpty()) {
            throw new Exception("존재하지 않는 메뉴입니다.");
        }
        Menu menu = optionalMenu.get();
        return new MenuResDto(menu);
    }

    public void deleteMenuByMenuIdx(int menuIdx) throws Exception {
        Optional<Menu> optionalMenu = menuRepository.findById(menuIdx);
        if (optionalMenu.isEmpty()) {
            throw new Exception("존재하지 않는 메뉴입니다.");
        }
        menuRepository.deleteById(menuIdx);
    }

    @Transactional
    public void updateMenu(int menuIdx, MenuReqDto menuReqDto, MultipartFile img) throws Exception {
        Optional<Menu> optionalMenu = menuRepository.findById(menuIdx);
        if (optionalMenu.isEmpty()) {
            throw new Exception("존재하지 않는 메뉴입니다.");
        }
        Menu menu = optionalMenu.get();
        Optional<Category> optionalCategory = categoryRepository.findById(menuReqDto.getCategory_idx());
        if (optionalCategory.isEmpty()) {
            throw new Exception("존재하지 않는 카테고리입니다.");
        }
        log.info("메뉴 검색 완");
        String url = s3Uploader.updateFile(img, menu.getMenuImage(), optionalCategory.get().getCategoryName());
        log.info(url);
        menu.update(menuReqDto, url);
        log.info("업데이트 완");
        menuRepository.save(menu);
    }
}
