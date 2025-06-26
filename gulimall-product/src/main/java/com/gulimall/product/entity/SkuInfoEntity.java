package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ✅ 商品服务模块 - SPU Part 4
 * 🎯 开发：SKU 信息维护模块（pms_sku_info 等）
 * 🧠 SKU 是什么？
 * SKU 是“最小库存单元”，商品最小粒度的组合。
 * 一个 SPU（产品） ➜ 可以有多个 SKU（颜色、版本、内存等组合）。
 */


@Data
@TableName("pms_sku_info")
public class SkuInfoEntity {

    @TableId
    private Long skuId;

    private Long spuId;

    private String skuName;

    private String skuDesc;

    private Long catalogId;

    private Long brandId;

    private String skuDefaultImg;

    private BigDecimal price;

    private Long saleCount;
}
