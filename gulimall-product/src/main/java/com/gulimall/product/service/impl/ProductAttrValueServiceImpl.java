package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.mapper.ProductAttrValueMapper;
import com.gulimall.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttrValueServiceImpl
        extends ServiceImpl<ProductAttrValueMapper, ProductAttrValueEntity>
        implements ProductAttrValueService {
}
