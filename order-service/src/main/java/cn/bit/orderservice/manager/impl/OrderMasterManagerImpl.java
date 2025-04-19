package cn.bit.orderservice.manager.impl;

import cn.bit.orderservice.manager.OrderMasterManager;
import cn.bit.orderservice.mapper.OrderMasterMapper;
import cn.bit.pojo.po.order.OrderMasterPO;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderMasterManagerImpl extends ServiceImpl<OrderMasterMapper, OrderMasterPO> implements OrderMasterManager {
    private final OrderMasterMapper orderMasterMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public Long insert(OrderMasterPO orderMasterPO) {
        Long id = idGenerator.nextId();
        orderMasterPO.setOrderId(id);
        orderMasterMapper.insert(orderMasterPO);
        return id;
    }
}
