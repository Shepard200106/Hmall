package com.gulimall.order.mq;

import com.gulimall.common.to.OrderTo;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "order.release.order",
        consumerGroup = "order-release-group"
)
public class OrderCloseListener implements RocketMQListener<OrderTo> {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(OrderTo orderTo) {
        log.info("🔔 收到超时订单消息：{}", orderTo.getOrderSn());

        // 查询订单状态，未支付就取消
        OrderEntity order = orderService.getOrderBySn(orderTo.getOrderSn());
        if (order != null && order.getStatus() == 0) { // 未支付
            order.setStatus(4); // 设置为已取消
            orderService.updateOrder(order);
            log.info("✅ 订单已关闭：{}", order.getOrderSn());
            // 发库存解锁消息
            rocketMQTemplate.convertAndSend("stock.release", orderTo);

        }
    }
}
