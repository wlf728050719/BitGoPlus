package cn.bit.pojo.po;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单操作日志实体类 对应数据库表：order_operation_log_[0-9]
 */
@Data
@Accessors(chain = true)
public class OrderOperationLogPO {
    /** 日志ID */
    private Long logId;

    /** 关联的订单ID */
    private Long orderId;

    /** 订单编号（冗余存储） */
    private String orderSn;

    /**
     * 操作人 可以是：用户ID、系统（system）、管理员ID等
     */
    private String operator;

    /**
     * 操作类型 1-创建订单, 2-支付, 3-发货, 4-确认收货, 5-取消, 6-退款, 7-修改订单
     */
    private Integer operationType;

    /** 操作描述 */
    private String operationDesc;

    /**
     * 操作参数（JSON格式） 记录操作时的关键数据快照
     */
    private String operationParams;

    /** 操作状态：0-失败, 1-成功 */
    private Integer operationStatus;

    /** 创建时间 */
    private LocalDateTime createTime;
}
