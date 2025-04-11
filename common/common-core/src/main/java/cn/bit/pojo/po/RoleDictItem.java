package cn.bit.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


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
}
