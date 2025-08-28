package com.gulimall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.common.to.OrderTo;
import com.gulimall.order.dao.OrderDao;
import com.gulimall.order.dao.OrderItemDao;
import com.gulimall.order.entity.OrderEntity;
import com.gulimall.order.entity.OrderItemEntity;
import com.gulimall.order.service.OrderService;
import com.gulimall.order.vo.OrderSubmitVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 导入
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @GlobalTransactional(name = "createOrderGlobalTx", rollbackFor = Exception.class)
    @Override
    public OrderEntity submitOrder(OrderSubmitVo vo) {
        // 1. 构建订单
        OrderEntity order = new OrderEntity();
        order.setOrderSn(UUID.randomUUID().toString().replace("-", ""));
        order.setMemberId(vo.getMemberId());
        order.setCreateTime(new Date());
        order.setStatus(0); // 未支付
        System.out.println(order);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderSubmitVo.OrderItemVo item : vo.getItems()) {
            System.out.println(item);
            BigDecimal itemTotal = item.getSkuPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        order.setTotalAmount(total);

        // 2. 保存订单主表
        orderDao.insert(order);
        System.out.println(order);
        // 3. 保存订单项
        for (OrderSubmitVo.OrderItemVo itemVo : vo.getItems()) {
            OrderItemEntity item = new OrderItemEntity();
            item.setOrderId(order.getId());                // 设置所属订单ID
            item.setSkuId(itemVo.getSkuId());              // 设置SKU
            item.setSpuName(itemVo.getSkuName());          // 商品名称
            item.setSkuPrice(itemVo.getSkuPrice());        // 商品单价
            item.setQuantity(itemVo.getQuantity());        // 数量
            System.out.println(item);
            BigDecimal itemTotal = itemVo.getSkuPrice().multiply(BigDecimal.valueOf(itemVo.getQuantity()));
            item.setTotalAmount(itemTotal);

            orderItemDao.insert(item);
        }
        // 构造消息体
        OrderTo orderTo = new OrderTo();
        orderTo.setOrderSn(order.getOrderSn());
        // 发送延迟消息：用于15分钟后关闭订单
        Message<OrderTo> message = MessageBuilder.withPayload(orderTo).build();
        rocketMQTemplate.syncSend(
                "order_release_order",// Topic
                message,
                3000,
                15
        );

        return order;
    }
    @Override
    public OrderEntity getOrderBySn(String orderSn) {
        return orderDao.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
    }

    @Override
    public void updateOrder(OrderEntity order) {
        orderDao.updateById(order);
    }

}
