package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SpuInfoDescEntity;
import com.gulimall.product.vo.SpuSaveVo;

public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {
    void saveSpuDesc(Long spuId, String decript);

}
