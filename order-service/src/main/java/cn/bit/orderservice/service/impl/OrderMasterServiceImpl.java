package cn.bit.orderservice.service.impl;

import cn.bit.orderservice.mapper.OrderMasterMapper;
import cn.bit.orderservice.service.OrderMasterService;
import cn.bit.pojo.po.OrderMasterPO;
import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMasterPO> implements OrderMasterService {
    private final OrderMasterMapper orderMasterMapper;
    private final DistributedSnowflakeIdGenerator idGenerator;

    @Override
    public int insertOrderMaster(OrderMasterPO orderMasterPO) {
        orderMasterPO.setOrderId(idGenerator.nextId());
        return orderMasterMapper.insert(orderMasterPO);
    }
}
