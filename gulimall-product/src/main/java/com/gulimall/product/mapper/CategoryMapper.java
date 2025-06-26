package com.gulimall.product.mapper;
//持久层（操作数据库）

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类数据库操作接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
}
