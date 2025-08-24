package com.gulimall.order.vo;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderSubmitVo {
    private Long memberId;  //
    private List<OrderItemVo> items;  // 提交的订单项列表
    @Data
    public static class OrderItemVo {

        private Long skuId;
        private String skuName;
        private BigDecimal skuPrice;
        private Integer quantity;
    }
}

