package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性 & 分组关联接口
 */
@RestController
@RequestMapping("/product/attrgroup/relation")
public class AttrAttrgroupRelationController {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 新增关联
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrAttrgroupRelationEntity relation) {
        relationService.save(relation);
        return R.ok()
                .put("message", "关联成功")
                .put("data", relation);
    }

    /**
     * 删除关联（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        relationService.removeByIds(ids);
        return R.ok().put("message", "删除成功");
    }

    /**
     * 查询所有关联关系
     */
    @GetMapping("/list")
    public R list() {
        List<AttrAttrgroupRelationEntity> list = relationService.list();
        return R.ok().put("data", list);
    }
}
