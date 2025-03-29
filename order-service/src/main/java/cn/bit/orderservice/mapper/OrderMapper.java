package cn.bit.orderservice.mapper;

import cn.bit.common.pojo.po.OrderPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    OrderPO getOrderPOById(@Param("id") Integer id);
}
