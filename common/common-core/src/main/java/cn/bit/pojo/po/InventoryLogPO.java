package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 库存变更记录实体类
 */
@Data
@Accessors(chain = true)
public class InventoryLogPO {
    /** 日志ID */
    private Long logId;

    /** 关联的SKU ID */
    private Long skuId;

    /** 变更数量（正数增加，负数减少） */
    private Integer changeAmount;

    /** 变更后库存 */
    private Integer currentStock;

    /** 关联订单号（出库时记录） */
    private String orderId;

    /**
     * 操作类型
     * 1-入库，2-出库，3-调整
     */
    private Integer operationType;

    /** 操作人 */
    private String operator;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;
}
