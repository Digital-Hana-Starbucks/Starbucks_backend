package com.hanaro.starbucks.dto.orders;

import lombok.Getter;

@Getter
public class OrderReqDto {
    private int menuIdx;
    private int orderDetailCount;
    private String menuTemperature;
    private String menuSize;
}
