package com.gulimall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.utils.R;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.service.SpuInfoService;
import com.gulimall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品 SPU 信息接口
 */
@RestController
@RequestMapping("/product/spuinfo")
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 查询所有 SPU（不分页）
     */
    @GetMapping("/list")
    public R list() {
        List<SpuInfoEntity> list = spuInfoService.list();
        return R.ok()
                .put("message", "查询 SPU 列表成功")
                .put("data", list);
    }

    /**
     * 根据 ID 查询 SPU 信息（可选）
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spu = spuInfoService.getById(id);
        return R.ok()
                .put("message", "查询 SPU 详情成功")
                .put("data", spu);
    }

    /**
     * 新增 SPU
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuInfoEntity spu) {
        spuInfoService.save(spu);
        return R.ok()
                .put("message", "新增 SPU 成功")
                .put("data", spu);
    }

    /**
     * 修改 SPU
     */
    @PutMapping("/update")
    public R update(@RequestBody SpuInfoEntity spu) {
        spuInfoService.updateById(spu);
        return R.ok()
                .put("message", "修改 SPU 成功")
                .put("data", spu);
    }

    /**
     * 删除 SPU（支持批量）
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        spuInfoService.removeByIds(ids);
        return R.ok()
                .put("message", "删除 SPU 成功");
    }
    /**
     * 商品统一保存接口：一次性保存 SPU + 图文 + 属性 + SKU
     */
    @PostMapping("/bigsave")
    public R bigSave(@RequestBody SpuSaveVo vo) {
        spuInfoService.saveSpuInfo(vo);
        return R.ok(); // ✅ 不加 msg 内容，符合你的返回规范
    }

    /**
     * 商品上架接口
     */
    @PutMapping("/up/{spuId}")
    public R spuUp(@PathVariable("spuId") Long spuId) {
        spuInfoService.up(spuId);
        return R.ok(); // 返回状态成功
    }
    //查询“未上架商品”接口
    @GetMapping("/unpublished")
    public R listUnpublished() {
        List<SpuInfoEntity> list = spuInfoService.list(
                new QueryWrapper<SpuInfoEntity>().eq("publish_status", 0)
        );
        return R.ok().put("data", list);
    }

}
