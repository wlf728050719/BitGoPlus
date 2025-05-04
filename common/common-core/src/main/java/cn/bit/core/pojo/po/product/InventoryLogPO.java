package cn.bit.core.pojo.po.product;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 库存变更记录实体类 对应数据库表：inventory_log_$->{0..9}
 */
@Data
@Accessors(chain = true)
@TableName(value = "inventory_log", autoResultMap = true)
public class InventoryLogPO {
    /** 日志ID */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;

    /** 关联的SKU ID */
    @TableField(value = "sku_id")
    private Long skuId;

    /** 变更数量（正数增加，负数减少） */
    @TableField(value = "change_amount")
    private Integer changeAmount;

    /** 变更后库存 */
    @TableField(value = "current_stock")
    private Integer currentStock;

    /** 关联订单号（出库时记录） */
    @TableField(value = "order_id")
    private String orderId;

    /** 操作类型 1-入库，2-出库，3-调整 */
    @TableField(value = "operation_type")
    private Integer operationType;

    /** 操作人 */
    @TableField(value = "operator")
    private String operator;

    /** 备注 */
    @TableField(value = "remark")
    private String remark;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
