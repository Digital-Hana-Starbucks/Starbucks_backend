package com.hanaro.starbucks.repository;

import com.hanaro.starbucks.entity.Category;
import com.hanaro.starbucks.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByCategory(Category category);
}
