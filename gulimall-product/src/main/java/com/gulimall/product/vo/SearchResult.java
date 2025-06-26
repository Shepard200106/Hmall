package com.gulimall.product.vo;

import com.gulimall.product.vo.es.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private List<SkuEsModel> products;
    private Long total;
    private Integer pageNum;
    private List<Long> brandIdAgg;
    private List<Long> catalogIdAgg;

}
