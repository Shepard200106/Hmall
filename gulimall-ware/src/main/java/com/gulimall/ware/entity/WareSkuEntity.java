package com.gulimall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ware_sku")
public class WareSkuEntity {
    private Long id;
    private Long skuId;
    private Long wareId;
    private Integer stock;        // 当前库存
    private Integer stockLocked;  // 已锁定库存
}
