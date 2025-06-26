package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.SkuImagesEntity;
import com.gulimall.product.mapper.SkuImagesMapper;
import com.gulimall.product.service.SkuImagesService;
import org.springframework.stereotype.Service;

@Service
public class SkuImagesServiceImpl
        extends ServiceImpl<SkuImagesMapper, SkuImagesEntity>
        implements SkuImagesService {}
