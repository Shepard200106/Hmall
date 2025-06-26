package com.gulimall.product.vo.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

// Lombok 注解：自动生成 getter/setter、toString、equals 等方法
@Data
public class SkuEsModel {

    // SKU 的唯一标识
    private Long skuId;

    // 所属的 SPU（商品聚合）ID
    private Long spuId;

    // SKU 的标题（用于展示、搜索）
    private String skuTitle;

    // SKU 的价格（支持精度的 BigDecimal 类型）
    private BigDecimal skuPrice;

    // SKU 的主图地址（图片 URL）
    private String skuImg;

    // 是否有库存，默认 true（用于搜索时显示“有货”或“无货”）
    private Boolean hasStock = true;

    // 热度评分，默认 0（用于搜索排序，随点击量等变化）
    private Long hotScore = 0L;

    // 品牌 ID
    private Long brandId;

    // 品牌名称（冗余字段，为了搜索展示方便）
    private String brandName;

    // 品牌图片地址
    private String brandImg;

    // 分类 ID（商品所属目录）
    private Long catalogId;

    // 分类名称（冗余字段）
    private String catalogName;

    // 商品的属性列表（用于筛选/搜索）
    private List<Attr> attrs;

    // 内部静态类：代表一个属性值对，比如“颜色=红色”、“内存=8GB”
    @Data
    public static class Attr {
        // 属性 ID
        private Long attrId;

        // 属性名（如颜色）
        private String attrName;

        // 属性值（如红色）
        private String attrValue;
    }
}

