package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.gulimall.product.mapper.SkuSaleAttrValueMapper;
import com.gulimall.product.service.SkuSaleAttrValueService;
import org.springframework.stereotype.Service;

@Service
public class SkuSaleAttrValueServiceImpl
        extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValueEntity>
        implements SkuSaleAttrValueService {}
