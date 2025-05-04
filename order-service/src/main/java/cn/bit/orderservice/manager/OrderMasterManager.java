package cn.bit.orderservice.manager;

import cn.bit.core.pojo.po.order.OrderMasterPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderMasterManager extends IService<OrderMasterPO> {
    Long insert(OrderMasterPO orderMasterPO);
}
