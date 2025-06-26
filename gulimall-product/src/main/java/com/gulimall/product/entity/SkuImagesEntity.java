package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pms_sku_images")
public class SkuImagesEntity {
    @TableId
    private Long id;

    private Long skuId;
    private String imgUrl;
    private Integer imgSort;
    private Integer defaultImg; // 0=非默认，1=默认
}
