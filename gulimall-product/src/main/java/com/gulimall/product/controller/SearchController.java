package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.service.ProductSearchService;
import com.gulimall.product.vo.SearchParam;
import com.gulimall.product.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping
    public R search(@RequestParam("keyword") String keyword) {
        SearchParam param = new SearchParam();
        param.setKeyword(keyword);  // 你得有 setKeyword 方法
        SearchResult result = productSearchService.search(param);
        return R.ok().put("data", result);
    }

}
