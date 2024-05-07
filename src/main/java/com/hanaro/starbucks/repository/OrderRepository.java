package com.hanaro.starbucks.repository;

import com.hanaro.starbucks.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByOrderByOrderIdxDesc();
}
