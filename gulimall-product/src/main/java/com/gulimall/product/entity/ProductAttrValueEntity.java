package com.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 平台属性（基本属性）值保存在哪？
 * 👉 就是保存到 pms_product_attr_value 表！
 * 每一个 SPU 都有多个基本属性值，比如：
 * SPU名称	属性名	属性值
 * 华为Mate60 Pro	屏幕尺寸	6.7英寸
 * 华为Mate60 Pro	运行内存	12GB
 */
@Data
@TableName("pms_product_attr_value")
public class ProductAttrValueEntity {

    @TableId
    private Long id;

    private Long spuId;

    private Long attrId;

    private String attrName;

    private String attrValue;

    private Integer quickShow;
}
