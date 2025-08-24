package com.gulimall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.ware.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
    // 查找有库存的记录
    WareSkuEntity selectAvailableStock(@Param("skuId") Long skuId, @Param("count") Integer count);
    void unlockStockByOrderSn(@Param("orderSn") String orderSn);

}
