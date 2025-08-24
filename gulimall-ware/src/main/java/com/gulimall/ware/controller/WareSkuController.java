package com.gulimall.ware.controller;


import com.gulimall.common.utils.R;
import com.gulimall.order.vo.SkuLockVo;
import com.gulimall.ware.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ware/stock")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping("/lock")
    public R lockStock(@RequestBody List<SkuLockVo> items){
        boolean success = wareSkuService.lockStock(items);
        return success ? R.ok() : R.error();
    }
}
