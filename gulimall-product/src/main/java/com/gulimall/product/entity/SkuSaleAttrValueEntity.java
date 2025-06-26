package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValueEntity {
    @TableId
    private Long id;

    private Long skuId;
    private Long attrId;
    private String attrName;
    private String attrValue;
    private Integer attrSort;
}
