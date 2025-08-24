package com.gulimall.product.mq;

// ✅ 文件：gulimall-search/src/main/java/com/gulimall/search/mq/ProductUpListener.java

import com.gulimall.product.service.ProductSearchService;
import com.gulimall.product.vo.es.SkuEsModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RocketMQMessageListener(topic = "product-up-topic", consumerGroup = "search-group")
public class ProductUpListener implements RocketMQListener<List<SkuEsModel>> {

    @Autowired
    private ProductSearchService productSearchService;

    @Override
    public void onMessage(List<SkuEsModel> models) {
        log.info("收到商品上架消息，准备写入 ES：{}", models.size());
        productSearchService.saveSku(models); // ✅ 将商品写入 ES
    }
}
