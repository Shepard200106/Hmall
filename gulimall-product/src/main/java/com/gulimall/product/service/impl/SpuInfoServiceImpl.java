package com.gulimall.product.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.product.entity.*;
import com.gulimall.product.mapper.SpuInfoMapper;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.SpuSaveVo;
import com.gulimall.product.vo.es.SkuEsModel;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private ProductAttrValueService attrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // 新增Redis依赖
    private static final Logger log = LoggerFactory.getLogger(SpuInfoServiceImpl.class);


    // 1. 你的代码：保存SPU信息
    @Override
    public void saveSpuInfo(SpuSaveVo vo){
        // ============ 1. 保存 SPU 基本信息 =============
        SpuInfoEntity spu = new SpuInfoEntity();
        spu.setSpuName(vo.getSpuName());
        spu.setSpuDescription(vo.getSpuDescription());
        spu.setCatalogId(vo.getCatalogId());
        spu.setBrandId(vo.getBrandId());
        spu.setPublishStatus(1); // 默认上架
        spu.setCreateTime(new Date());
        spu.setUpdateTime(new Date());
        this.save(spu); // 保存 SPU
        Long spuId = spu.getId();

        // ============ 2. 保存图文描述（SPU_DESC） =============
        SpuInfoDescEntity desc = new SpuInfoDescEntity();
        desc.setSpuId(spuId);
        desc.setDecript(vo.getDecript());
        spuInfoDescService.save(desc);

        // ============ 3. 保存基本属性值 =============
        List<ProductAttrValueEntity> baseAttrEntities = vo.getBaseAttrs().stream().map(attr -> {
            ProductAttrValueEntity val = new ProductAttrValueEntity();
            val.setAttrId(attr.getAttrId());
            val.setAttrName(attr.getAttrName());
            val.setAttrValue(attr.getAttrValue());
            val.setQuickShow(attr.getQuickShow());
            val.setSpuId(spuId);
            return val;
        }).collect(Collectors.toList());
        attrValueService.saveBatch(baseAttrEntities);

        // ============ 4. 保存所有 SKU =============
        for (SpuSaveVo.SkuVo skuVo : vo.getSkus()) {
            SkuInfoEntity sku = new SkuInfoEntity();
            sku.setSpuId(spuId);
            sku.setSkuName(skuVo.getSkuName());
            sku.setSkuDesc(skuVo.getSkuDesc());
            sku.setPrice(skuVo.getPrice());
            sku.setSkuDefaultImg(skuVo.getSkuDefaultImg());
            sku.setBrandId(skuVo.getBrandId());
            sku.setCatalogId(skuVo.getCatalogId());
            sku.setSaleCount(0L);
            skuInfoService.save(sku); // 保存 SKU
            Long skuId = sku.getSkuId();

            // 2.2 保存 sku 图片
            List<SkuImagesEntity> images = skuVo.getImages().stream()
                    .map(url -> {
                        SkuImagesEntity img = new SkuImagesEntity();
                        img.setSkuId(skuId);
                        img.setImgUrl(url);
                        img.setImgSort(0);
                        img.setDefaultImg(url.equals(skuVo.getSkuDefaultImg()) ? 1 : 0);
                        return img;
                    }).collect(Collectors.toList());
            skuImagesService.saveBatch(images);

            // 2.3 保存 sku 销售属性
            List<SkuSaleAttrValueEntity> saleAttrs = skuVo.getAttr().stream()
                    .map(attr -> {
                        SkuSaleAttrValueEntity val = new SkuSaleAttrValueEntity();
                        val.setSkuId(skuId);
                        val.setAttrId(attr.getAttrId());
                        val.setAttrName(attr.getAttrName());
                        val.setAttrValue(attr.getAttrValue());
                        val.setAttrSort(0);
                        return val;
                    }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(saleAttrs);
        }
    }

    @Transactional
    // 2. 你的代码：SPU上架
    @Override
    public void up(Long spuId){
        // 1. 查出当前 spu 下所有 sku
        List<SkuInfoEntity> skuList = skuInfoService.list(
                new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId)
        );
        // 2. 将每个 sku 转换为 Elasticsearch 文档对象（SkuEsModel）
        List<SkuEsModel> esData = skuList.stream().map(sku ->{
            SkuEsModel model = new SkuEsModel();
            model.setSkuId(sku.getSkuId());
            model.setSpuId(spuId);
            model.setSkuTitle(sku.getSkuName());
            model.setSkuPrice(sku.getPrice());
            model.setSkuImg(sku.getSkuDefaultImg());
            model.setBrandId(sku.getBrandId());
            model.setCatalogId(sku.getCatalogId());
            return model;
        }).toList();

        // 3. 发送 RocketMQ 消息（同步到ES）
        rocketMQTemplate.syncSend("product-up-topic", esData);
        log.info("发送商品上架消息到 MQ，spuId={}, sku数量={}", spuId, esData.size());

        // 4. 修改SPU发布状态为已上架
        SpuInfoEntity spu = this.getById(spuId);
        spu.setPublishStatus(1);
        this.updateById(spu);
    }


    // 3. 合并：带缓存的SPU查询（修改方法名为getSpuById，更规范）
    @Override
    public SpuInfoEntity getSpuById(Long spuId) {
        // 1. 定义缓存key（格式：spu:info:SPU ID）
        String cacheKey = "spu:info:" + spuId;

        // 2. 先查Redis缓存
        SpuInfoEntity spu = (SpuInfoEntity) redisTemplate.opsForValue().get(cacheKey);
        if (spu != null) {
            log.info("从Redis缓存获取SPU信息，ID：{}", spuId);
            return spu; // 缓存命中，直接返回
        }

        // 3. 缓存未命中，查数据库
        log.info("缓存未命中，从数据库查询SPU信息，ID：{}", spuId);
        spu = baseMapper.selectById(spuId);

        if (spu == null) {
            // 处理缓存穿透：缓存空值（5分钟过期）
            redisTemplate.opsForValue().set(cacheKey, null, 5, TimeUnit.MINUTES);
            return null;
        }

        // 4. 数据库结果存入Redis（10-15分钟过期，防雪崩）
        long expireTime = 10 + new Random().nextInt(5);
        redisTemplate.opsForValue().set(cacheKey, spu, expireTime, TimeUnit.MINUTES);
        log.info("SPU信息存入Redis，ID：{}，过期时间：{}分钟", spuId, expireTime);

        return spu;
    }
}
