package cn.bit.orderservice.service.impl;

import cn.bit.orderservice.mapper.OrderItemMapper;
import cn.bit.orderservice.service.OrderItemService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
