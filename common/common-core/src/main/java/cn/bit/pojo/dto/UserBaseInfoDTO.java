package cn.bit.pojo.dto;


import cn.bit.pojo.po.UserPO;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UserBaseInfoDTO {
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

    public UserBaseInfoDTO(@NonNull UserPO userPO) {
        this.userId = userPO.getUserId();
        this.username = userPO.getUsername();
        this.realName = userPO.getRealName();
        this.nickname = userPO.getNickname();
        this.avatar = userPO.getAvatar();
        this.birthDate = userPO.getBirthDate();
        this.phone = userPO.getPhone();
        this.email = userPO.getEmail();
        this.qq = userPO.getQq();
        this.wechat = userPO.getWechat();
        this.gender = userPO.getGender();
        this.idCard = userPO.getIdCard();
    }
}
