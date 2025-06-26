package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.mapper.BrandMapper;
import com.gulimall.product.service.BrandService;
import org.springframework.stereotype.Service;

/**
 * 品牌业务实现类
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

}
