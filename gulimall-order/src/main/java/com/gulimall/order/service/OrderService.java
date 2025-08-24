package com.gulimall.order.service;

import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.vo.OrderSubmitVo;

public interface OrderService {
    OrderEntity submitOrder(OrderSubmitVo vo);
    OrderEntity getOrderBySn(String orderSn);
    void updateOrder(OrderEntity order);

}