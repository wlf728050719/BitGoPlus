package cn.bit.orderservice.service;

import cn.bit.pojo.po.OrderMasterPO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderMasterService extends IService<OrderMasterPO> {
    int insertOrderMaster(OrderMasterPO orderMasterPO);
}
