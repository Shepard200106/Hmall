package com.gulimall.product.vo;

import lombok.Data;

@Data
public class SearchParam {
    private String keyword;
    private Integer pageNum = 1;
    private Long catalogId;
    private Long brandId;
    private Boolean hasStock;      // 是否有货
    private String priceRange;     // 价格区间，格式如 100_500、_500、100_

}
