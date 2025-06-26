package com.gulimall.product.controller;

import com.gulimall.common.utils.R;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性分组接口
 */
@RestController
@RequestMapping("/product/attrgroup")
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 查询所有分组（不分页）
     */
    @GetMapping("/list")
    public R list() {
        List<AttrGroupEntity> list = attrGroupService.list();
        return R.ok().put("data", list);
    }

    /**
     * 查询分组详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        AttrGroupEntity entity = attrGroupService.getById(id);
        return R.ok().put("data", entity);
    }

    /**
     * 新增分组
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity entity) {
        attrGroupService.save(entity);
        return R.ok()
                .put("message", "分组保存成功")
                .put("data", entity);
    }

    /**
     * 修改分组
     */
    @PutMapping("/update")
    public R update(@RequestBody AttrGroupEntity entity) {
        attrGroupService.updateById(entity);
        return R.ok()
                .put("message", "分组修改成功")
                .put("data", entity);
    }

    /**
     * 删除分组（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        attrGroupService.removeByIds(ids);
        return R.ok().put("message", "分组删除成功");
    }
}
