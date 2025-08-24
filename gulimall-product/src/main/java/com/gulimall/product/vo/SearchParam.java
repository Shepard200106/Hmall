package com.gulimall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {
    private String keyword;
    private Integer pageNum = 1;
    private Long catalogId;
    private Long brandId;
    private Boolean hasStock;      // 是否有货
    private String priceRange;     // 价格区间，格式如 100_500、_500、100_

    private List<String> attrs; // 示例：["1_白色", "2_8G:16G"]
}
