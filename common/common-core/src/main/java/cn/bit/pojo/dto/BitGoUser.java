package cn.bit.pojo.dto;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;


@Getter
public class BitGoUser extends User {
    /** 用户ID（雪花ID） */
    private final Long userId;

    /** 真实姓名 */
    private final String realName;

    /** 昵称 */
    private final String nickname;

    /** 头像URL */
    private final String avatar;

    /** 出生日期 */
    private final Date birthDate;

    /** 手机号 */
    private final String phone;

    /** 邮箱 */
    private final String email;

    /** QQ号 */
    private final String qq;

    /** 微信号 */
    private final String wechat;

    /** 性别（0-未知，1-男，2-女） */
    private final Integer gender;

    /** 身份证号 */
    private final String idCard;

    /** 锁定标志（0-未锁定，1-已锁定） */
    private final Integer lockFlag;

    /** 删除标志（0-未删除，1-已删除） */
    private final Integer delFlag;


    public BitGoUser(UserBaseInfo userPO, Collection<? extends GrantedAuthority> authorities) {
        super(userPO.getUsername(), userPO.getPassword(), authorities);
        this.userId = userPO.getUserId();
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
        this.lockFlag = userPO.getLockFlag();
        this.delFlag = userPO.getDelFlag();
    }
}
