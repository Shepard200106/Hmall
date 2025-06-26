package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.service.SpuInfoDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品 SPU 描述信息控制器
 */
@RestController
@RequestMapping("/product/spuinfodesc")
public class SpuInfoDescController {

    @Autowired
    private SpuInfoDescService descService;

    /**
     * 新增 SPU 描述
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuInfoDescEntity desc) {
        descService.save(desc);
        return R.ok()
                .put("message", "新增 SPU 描述成功")
                .put("data", desc);
    }

    /**
     * 获取 SPU 描述详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long spuId) {
        SpuInfoDescEntity entity = descService.getById(spuId);
        return R.ok()
                .put("message", "获取 SPU 描述成功")
                .put("data", entity);
    }

    /**
     * 更新 SPU 描述
     */
    @PutMapping("/update")
    public R update(@RequestBody SpuInfoDescEntity desc) {
        descService.updateById(desc);
        return R.ok()
                .put("message", "更新 SPU 描述成功")
                .put("data", desc);
    }

    /**
     * 删除 SPU 描述（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        descService.removeByIds(ids);
        return R.ok()
                .put("message", "删除 SPU 描述成功");
    }
}
