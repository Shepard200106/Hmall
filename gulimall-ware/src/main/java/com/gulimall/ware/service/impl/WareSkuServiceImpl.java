package com.gulimall.ware.service.impl;

import com.gulimall.order.vo.SkuLockVo;
import com.gulimall.ware.dao.WareSkuDao;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WareSkuServiceImpl implements WareSkuService {
    @Autowired
    private WareSkuDao wareSkuDao;
    @Override
    @Transactional
    public boolean lockStock(List<SkuLockVo> items){
        for(SkuLockVo vo: items){
            WareSkuEntity sku = wareSkuDao.selectAvailableStock(vo.getSkuId(), vo.getCount());
            if(sku == null){
                return false;
            }
            sku.setStockLocked(sku.getStockLocked() + vo.getCount());
            wareSkuDao.updateById(sku);
        }
        return true;
    }
}
