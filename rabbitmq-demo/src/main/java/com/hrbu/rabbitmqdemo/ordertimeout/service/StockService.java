package com.hrbu.rabbitmqdemo.ordertimeout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
//    @Autowired
//    private StockMapper stockMapper;

    // 创建订单时预扣库存（扣减冻结库存，不是真实扣减）
    public void preOccupyStock(String orderId, String skuId, Integer quantity) {
        // SQL: update stock set frozen_quantity = frozen_quantity + quantity where sku_id = xxx and (available_quantity - frozen_quantity) >= quantity
        // 实现略
    }

    // 订单取消时恢复库存（释放冻结库存）
    public void restoreStock(String orderId) {
        // 根据订单id查询商品明细，然后 update stock set frozen_quantity = frozen_quantity - quantity
        // 实现略
    }
}