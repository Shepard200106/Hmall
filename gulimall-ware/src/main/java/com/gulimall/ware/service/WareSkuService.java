package com.gulimall.ware.service;



import com.gulimall.order.vo.SkuLockVo;

import java.util.List;

public interface WareSkuService {
    boolean lockStock(List<SkuLockVo> items);
}
