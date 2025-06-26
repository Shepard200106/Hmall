package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性管理接口
 */
@RestController
@RequestMapping("/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    /**
     * 查询所有属性（可加分页、筛选）
     */
    @GetMapping("/list")
    public R list() {
        List<AttrEntity> list = attrService.list();
        return R.ok().put("data", list);
    }

    /**
     * 查询属性详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AttrEntity attr = attrService.getById(id);
        return R.ok().put("data", attr);
    }

    /**
     * 保存属性
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrEntity attr) {
        attrService.save(attr);
        return R.ok()
                .put("message", "属性保存成功")
                .put("data", attr);
    }

    /**
     * 修改属性
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrEntity attr) {
        attrService.updateById(attr);
        return R.ok()
                .put("message", "属性修改成功")
                .put("data", attr);
    }

    /**
     * 删除属性
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        attrService.removeByIds(ids);
        return R.ok().put("message", "属性删除成功");
    }
}
