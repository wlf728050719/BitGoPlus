package cn.bit.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@Accessors(chain = true)
public class UserPO {
    /**
     * 用户ID（雪花ID）
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 加密盐值
     */
    private String salt;
    /**
     * 头像URL
     */
    private String avatar;
    /**
     * 出生日期
     */
    private LocalDate birthDate;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 性别（0-未知，1-男，2-女）
     */
    private Integer gender;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 上次修改密码时间
     */
    private LocalDateTime lastPasswordChangeTime;
    /**
     * 锁定标志（0-未锁定，1-已锁定）
     */
    private Integer lockFlag;
    /**
     * 删除标志（0-未删除，1-已删除）
     */
    private Integer delFlag;
}
