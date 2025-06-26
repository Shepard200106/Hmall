package com.gulimall.product.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 *构建“一个完整商品”的数据体系：
 *
 * 数据类型	表名	说明
 * SPU主信息	pms_spu_info	一个商品的整体描述（如：华为Mate60）
 * SPU描述图文	pms_spu_info_desc	富文本介绍、图文详情页
 * SPU属性值	pms_product_attr_value	基本属性的实际值（颜色=红）
 * SKU表	pms_sku_info	每个规格组合成的实际商品（如：Mate60 Pro 白 12+256）
 * SKU图片	pms_sku_images	每个 SKU 的图片
 * SKU销售属性	pms_sku_sale_attr_value	每个 SKU 的销售属性（颜色、版本）
 */

/**
 * 商品spu实体
 */
@Data
@TableName("pms_spu_info")
public class SpuInfoEntity {
    @TableId
    private Long id;

    private String spuName;

    private String spuDescription;

    private Long catalogId;

    private Long brandId;

    private Integer publishStatus;

    private Date createTime;

    private Date updateTime;
}
