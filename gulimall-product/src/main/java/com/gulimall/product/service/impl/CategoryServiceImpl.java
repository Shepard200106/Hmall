package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//分类业务逻辑实现
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {
    @Override
    public List<CategoryEntity> listWithTree(){
        // 1. 查所有
        List<CategoryEntity> all = baseMapper.selectList(null);
        // 2. 找出一级分类（parentCid == 0），并递归构建子分类
        return all.stream()
                .filter(cat -> cat.getParentCid() == 0)
                .map(cat -> {
                    cat.setChildren(getChildren(cat, all));
                    return cat;
                }).collect(Collectors.toList());
    }

    /**
     * 递归获取子分类
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        return all.stream()
                .filter(cat -> cat.getParentCid().equals(root.getCatId()))
                .map(cat -> {
                    cat.setChildren(getChildren(cat, all)); // 递归
                    return cat;
                }).collect(Collectors.toList());
    }

    @Override
    public void removeMenusByIds(List<Long> ids){
        baseMapper.deleteBatchIds(ids);
    }
}
