package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类管理接口
 */
@RestController
@RequestMapping("/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 树形查询分类列表
     */
    @GetMapping("/list/tree")
    public R listTree() {
        List<CategoryEntity> list = categoryService.listWithTree();
        return R.ok()
                .put("message", "分类树查询成功")
                .put("data", list);
    }

    /**
     * 新增分类
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryEntity entity) {
        categoryService.save(entity);
        return R.ok()
                .put("message", "分类新增成功")
                .put("data", entity);
    }

    /**
     * 修改分类
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity entity) {
        categoryService.updateById(entity);
        return R.ok()
                .put("message", "分类修改成功")
                .put("data", entity);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        categoryService.removeMenusByIds(ids);
        return R.ok().put("message", "分类删除成功");
    }
}
