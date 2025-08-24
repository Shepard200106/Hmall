package com.gulimall.order.controller;

import com.gulimall.common.utils.R;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.service.OrderService;
import com.gulimall.order.vo.OrderSubmitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public R submitOrder(@RequestBody OrderSubmitVo vo) {
        OrderEntity order = orderService.submitOrder(vo);
        return R.ok().put("order", order);
    }
}
