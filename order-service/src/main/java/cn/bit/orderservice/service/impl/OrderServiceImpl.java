package cn.bit.orderservice.service.impl;

import cn.bit.common.pojo.po.OrderPO;
import cn.bit.common.pojo.vo.OrderInfoVO;
import cn.bit.common.pojo.dto.UserBaseInfoDTO;
import cn.bit.feign.client.UserClient;
import cn.bit.orderservice.mapper.OrderMapper;
import cn.bit.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;
    @Override
    public OrderInfoVO getOrderInfoById(int orderId) {
        OrderPO orderPO = orderMapper.getOrderPOById(orderId);
        if (orderPO == null)
            return null;

        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setOrderId(orderPO.getId());
        orderInfoVO.setAmount(orderPO.getAmount());
        orderInfoVO.setCreateTime(orderPO.getCreateTime());
        orderInfoVO.setGetLocation(orderPO.getGetLocation());

        try {
            // 获取买家信息
            UserBaseInfoDTO userBaseInfo1 = userClient.getUserBaseInfo(orderPO.getBuyerId(),"request from order").getData();
            orderInfoVO.setBuyerName(userBaseInfo1.getUsername());

            // 获取卖家信息
            UserBaseInfoDTO userBaseInfo2 = userClient.getUserBaseInfo(orderPO.getSellerId(),"request from order").getData();
            orderInfoVO.setSellerName(userBaseInfo2.getUsername());
        } catch (Exception e) {
            System.out.println("user-service connect error");
            e.printStackTrace();
        }
        return orderInfoVO;
    }
}
