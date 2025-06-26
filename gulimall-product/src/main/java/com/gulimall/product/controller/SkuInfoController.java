package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SkuInfoEntity;
import com.gulimall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SKU 信息管理接口
 */
@RestController
@RequestMapping("/product/skuinfo")
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 查询所有 SKU 信息
     */
    @GetMapping("/list")
    public R list() {
        List<SkuInfoEntity> list = skuInfoService.list();
        return R.ok()
                .put("message", "查询成功")
                .put("data", list);
    }

    /**
     * 新增 SKU
     */
    @PostMapping("/save")
    public R save(@RequestBody SkuInfoEntity sku) {
        skuInfoService.save(sku);
        return R.ok()
                .put("message", "保存成功")
                .put("data", sku);
    }

    /**
     * 修改 SKU
     */
    @PutMapping("/update")
    public R update(@RequestBody SkuInfoEntity sku) {
        skuInfoService.updateById(sku);
        return R.ok()
                .put("message", "修改成功")
                .put("data", sku);
    }

    /**
     * 删除 SKU（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        skuInfoService.removeByIds(ids);
        return R.ok()
                .put("message", "删除成功");
    }
}
