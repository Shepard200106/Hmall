package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.mapper.AttrMapper;
import com.gulimall.product.service.AttrService;
import org.springframework.stereotype.Service;

/**
 * 商品属性业务实现类
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {
}
