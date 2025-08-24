package com.gulimall.order.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("oms_order")
public class OrderEntity {
    @TableId
    private Long id;
    private String orderSn;
    private Long memberId;
    private BigDecimal totalAmount;
    private Integer status;
    private Date createTime;
    private Date payTime;
}
