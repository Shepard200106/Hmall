package com.gulimall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuSaveVo;

public interface SpuInfoService extends IService<SpuInfoEntity> {
    void saveSpuInfo(SpuSaveVo vo);
    void up(Long spuId);
}
