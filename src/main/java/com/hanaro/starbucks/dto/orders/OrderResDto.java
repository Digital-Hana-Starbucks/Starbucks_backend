package com.hanaro.starbucks.dto.orders;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Builder
@Getter
public class OrderResDto {
    private int orderIdx;

    private String orderId;
    private Integer totalPrice;

    private String orderStatus;

    private LocalDate orderDate;

}
