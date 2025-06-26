package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.utils.R;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SPU规格参数（属性值）控制器
 */
@RestController
@RequestMapping("/product/attrvalue")
public class ProductAttrValueController {

    @Autowired
    private ProductAttrValueService attrValueService;

    /**
     * 获取指定 SPU 的属性值列表
     */
    @GetMapping("/list/{spuId}")
    public R listBySpu(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> list = attrValueService
                .list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        return R.ok()
                .put("message", "获取成功")
                .put("data", list);
    }

    /**
     * 保存属性值
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductAttrValueEntity entity) {
        attrValueService.save(entity);
        return R.ok()
                .put("message", "保存成功")
                .put("data", entity);
    }

    /**
     * 更新属性值
     */
    @PutMapping("/update")
    public R update(@RequestBody ProductAttrValueEntity entity) {
        attrValueService.updateById(entity);
        return R.ok()
                .put("message", "更新成功")
                .put("data", entity);
    }

    /**
     * 删除属性值（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        attrValueService.removeByIds(ids);
        return R.ok()
                .put("message", "删除成功");
    }
}
