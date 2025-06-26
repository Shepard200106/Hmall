package com.gulimall.product.service;
//业务接口
import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.CategoryEntity;

import java.util.List;

/**
 * 分类业务接口
 */
public interface CategoryService  extends IService<CategoryEntity> {
    //    查询所有分类并以树形结构返回（递归）
    List<CategoryEntity> listWithTree();
    //    删除分类
    void removeMenusByIds(List<Long> ids);
}
