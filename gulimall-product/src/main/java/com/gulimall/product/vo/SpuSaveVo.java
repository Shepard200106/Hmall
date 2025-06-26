package com.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前端提交的商品保存表单（VO 封装类）
 */
@Data
public class SpuSaveVo {
    // ============ SPU 基本信息 ============
    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;

    // ============ 图文详情（多图可逗号分隔） ============
    private String decript;

    // ============ 基本属性值 ============
    private List<BaseAttr> baseAttrs;

    // ============ SKU 信息 ============
    private List<SkuVo> skus;

    @Data
    public static class BaseAttr {
        private Long attrId;
        private String attrName;
        private String attrValue;
        private Integer quickShow;
    }

    @Data
    public static class SkuVo {
        private String skuName;
        private String skuDesc;
        private BigDecimal price;
        private String skuDefaultImg;
        private Long brandId;
        private Long catalogId;
        private List<SaleAttr> attr; // 销售属性
        private List<String> images; // 多图地址
    }

    @Data
    public static class SaleAttr {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
