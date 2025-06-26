package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理接口
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 获取所有品牌
     */
    @GetMapping("/list")
    public R list() {
        List<BrandEntity> list = brandService.list();
        return R.ok().put("data", list);
    }

    /**
     * 保存品牌
     */
    @PostMapping("/save")
    public R save(@RequestBody BrandEntity brand) {
        brandService.save(brand);
        return R.ok()
                .put("message", "品牌新增成功")
                .put("data", brand);
    }

    /**
     * 修改品牌
     */
    @PutMapping("/update")
    public R update(@RequestBody BrandEntity brand) {
        brandService.updateById(brand);
        return R.ok()
                .put("message", "品牌修改成功")
                .put("data", brand);
    }

    /**
     * 删除品牌（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        brandService.removeByIds(ids);
        return R.ok().put("message", "品牌删除成功");
    }
}
