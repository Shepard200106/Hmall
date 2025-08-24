package com.gulimall.ware.mq;

import com.gulimall.common.to.OrderTo;
import com.gulimall.ware.dao.WareSkuDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "stock.release",
        consumerGroup = "stock-release-group"
)
public class StockReleaseListener implements RocketMQListener<OrderTo> {

    @Autowired
    private WareSkuDao wareSkuDao;

    @Override
    public void onMessage(OrderTo orderTo) {
        log.info("🔓 收到订单取消通知，准备释放库存：{}", orderTo.getOrderSn());

        // 根据 orderSn 解锁库存
        wareSkuDao.unlockStockByOrderSn(orderTo.getOrderSn());
    }
}
