package cn.bit.orderservice.service;

import cn.bit.common.pojo.vo.OrderInfoVO;

public interface OrderService {
    OrderInfoVO getOrderInfoById(int orderId);
}
