package com.gulimall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("oms_order_item")
public class OrderItemEntity {
    @TableId
    private Long id;
    private Long orderId;
    private Long skuId;
    private String spuName;
    private BigDecimal skuPrice;
    private Integer quantity;
    private BigDecimal totalAmount;
}
