package com.gulimall.product.service;

import com.gulimall.product.vo.SearchParam;
import com.gulimall.product.vo.SearchResult;

public interface ProductSearchService {
    SearchResult search(SearchParam searchParam);
}
