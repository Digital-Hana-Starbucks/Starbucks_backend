package com.hanaro.starbucks.dto.menu;


import lombok.Getter;

@Getter
public class MenuReqDto {

    private String menuName;

    private int menuPrice;


    private int categoryIdx;
}
