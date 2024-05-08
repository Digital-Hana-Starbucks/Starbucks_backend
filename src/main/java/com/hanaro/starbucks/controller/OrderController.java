package com.hanaro.starbucks.controller;

import com.hanaro.starbucks.dto.orders.OrderEditReqDto;
import com.hanaro.starbucks.dto.orders.OrderReqDto;
import com.hanaro.starbucks.dto.orders.OrderResDto;
import com.hanaro.starbucks.service.OrderService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hanaro.starbucks.util.APIConstant.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/orders")
@CrossOrigin("http://localhost:5173")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public List<OrderResDto> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{orderIdx}")
    public OrderResDto getOrder(@PathVariable int orderIdx) {
        return orderService.getOrder(orderIdx);
    }

    @PutMapping("/admin/{orderIdx}")
    public void updateOrder(@PathVariable int orderIdx, @RequestBody OrderEditReqDto orderEditReqDto) {
        orderService.updateOrder(orderIdx, orderEditReqDto);
    }

    @DeleteMapping("/admin/{orderIdx}")
    public void deleteOrder(@PathVariable int orderIdx) {
        orderService.deleteOrder(orderIdx);
    }

    @PostMapping("")
    public void createOrder(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<OrderReqDto> dtos) {
        String originalToken = headers.getFirst("authorization");
        if(headers.containsKey("authorization") ){
            String token = originalToken.substring(7, originalToken.length());
            orderService.createOrder(token, dtos);
        }else{
            orderService.createOrder("null", dtos);
        }


    }

}
