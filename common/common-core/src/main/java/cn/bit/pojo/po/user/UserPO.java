package cn.bit.pojo.po.user;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 用户实体类
 */
@Data
@Accessors(chain = true)
@TableName(value = "user", autoResultMap = true)
public class UserPO {
    /** 用户ID（雪花ID） */
    @TableId(value = "user_id")
    private Long userId;

    /** 用户名 */
    @TableField("username")
    private String username;

    /** 真实姓名 */
    @TableField("real_name")
    private String realName;

    /** 昵称 */
    @TableField("nickname")
    private String nickname;

    /** 加密后的密码 */
    @TableField("password")
    private String password;

    /** 头像URL */
    @TableField("avatar")
    private String avatar;

    /** 出生日期 */
    @TableField("birth_date")
    private Date birthDate;
    /** 手机号 */
    @TableField("phone")
    private String phone;

    /** 邮箱 */
    @TableField("email")
    private String email;

    /** QQ号 */
    @TableField("qq")
    private String qq;

    /** 微信号 */
    @TableField("wechat")
    private String wechat;

    /** 性别（0-未知，1-男，2-女） */
    @TableField("gender")
    private Integer gender;

    /** 身份证号 */
    @TableField("id_card")
    private String idCard;

    /** 创建时间 */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 上次登录时间 */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /** 最后登录IP */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /** 上次修改密码时间 */
    @TableField("last_password_change_time")
    private LocalDateTime lastPasswordChangeTime;

    /** 锁定标志（0-未锁定，1-已锁定） */
    @TableField("lock_flag")
    private Integer lockFlag;

    /** 删除标志（0-未删除，1-已删除） */
    @TableField("del_flag")
    private Integer delFlag;
}
