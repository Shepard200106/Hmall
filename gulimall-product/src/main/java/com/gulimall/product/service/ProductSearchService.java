package com.gulimall.product.service;

import com.gulimall.product.vo.SearchParam;
import com.gulimall.product.vo.SearchResult;
import com.gulimall.product.vo.es.SkuEsModel;

import java.util.List;

public interface ProductSearchService {
    SearchResult search(SearchParam searchParam);

    void saveSku(List<SkuEsModel> models);
}
