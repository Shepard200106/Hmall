package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 品牌实体类
 */
@Data
@TableName("pms_brand")
public class BrandEntity {
    @TableId
    private Long brandId;
    private String name;
    private String logo;
    private String descript;
    private String showStatus;
    private String firstLetter;
    private Integer sort;
}
