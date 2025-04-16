package cn.bit.pojo.po.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@TableName(value = "dict_role", autoResultMap = true)
public class RoleDictItem implements Serializable {
    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @TableField("role_id")
    private Long roleId;

    /** 角色码 */
    @TableField("role_code")
    private String roleCode;

    /** 角色描述 */
    @TableField("description")
    private String description;

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
