package com.gulimall.order.fegin;

import com.gulimall.order.vo.SkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WareFeignService {

    @PostMapping("/ware/waresku/lock")
    com.gulimall.common.utils.R lockStock(@RequestBody List<SkuLockVo> vos);
}
