package cn.bit.pojo.po.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "permission", autoResultMap = true)
public class PermissionPO {
    /** 用户ID */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    /** 角色ID */
    private Long roleId;

    /** 租户ID */
    @TableField("tenant_id")
    private Long tenantId;
    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标志（0-未删除，1-已删除） */
    @TableField("del_flag")
    private Integer delFlag;

}
