package com.hanaro.starbucks.dto.orders;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Getter
public class OrderResDto {
    private Integer userIdx;
    private String userNickname;
    private int orderIdx;
    private String orderId;
    private Integer totalPrice;
    private String orderStatus;
    private LocalDateTime orderDate;

}
