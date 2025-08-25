package com.gulimall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuSaveVo;

public interface SpuInfoService extends IService<SpuInfoEntity> {
    void saveSpuInfo(SpuSaveVo vo);
    void up(Long spuId);
    // 3. 合并：带缓存的SPU查询（修改方法名为getSpuById，更规范）
    SpuInfoEntity getSpuById(Long spuId);
}
