package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.mapper.SpuInfoDescMapper;
import com.gulimall.product.service.SpuInfoDescService;
import com.gulimall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescMapper, SpuInfoDescEntity> implements SpuInfoDescService {
    @Override
    public void saveSpuDesc(Long spuId, String decript) {
        SpuInfoDescEntity desc = new SpuInfoDescEntity();
        desc.setSpuId(spuId);
        desc.setDecript(decript);
        this.save(desc);
    }


}
