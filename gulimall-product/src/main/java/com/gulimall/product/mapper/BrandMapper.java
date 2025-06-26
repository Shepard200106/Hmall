package com.gulimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.product.entity.BrandEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌数据库操作
 */
@Mapper
public interface BrandMapper extends BaseMapper<BrandEntity> {
}
