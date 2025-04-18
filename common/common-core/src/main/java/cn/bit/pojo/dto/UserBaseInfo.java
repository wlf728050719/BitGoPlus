package cn.bit.pojo.dto;

import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.pojo.po.user.UserPO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserBaseInfo {
    /** 用户ID **/
    @Null
    private Long userId;

    /** 用户名 */
    @ValidString(StringEnum.USERNAME_STRING)
    private String username;

    /** 真实姓名 */
    private String realName;

    @Null
    private Integer realNameVerify;

    /** 昵称 */
    private String nickname;

    /** 密码明文 */
    @ValidString(StringEnum.PASSWORD_STRING)
    private String password;

    /** 头像URL */
    private String avatar;

    /** 出生日期 */
    private Date birthDate;

    @Null
    private Integer birthDateVerify;

    /** 手机号 */
    @ValidString(StringEnum.PHONE_STRING)
    private String phone;

    @Null
    private Integer phoneVerify;

    /** 邮箱 */
    @ValidString(value = StringEnum.EMAIL_STRING, allowEmpty = true)
    private String email;

    @Null
    private Integer emailVerify;

    /** QQ号 */
    @ValidString(value = StringEnum.QQ_STRING, allowEmpty = true)
    private String qq;

    @Null
    private Integer qqVerify;

    /** 微信号 */
    @ValidString(value = StringEnum.WECHAT_STRING, allowEmpty = true)
    private String wechat;

    @Null
    private Integer wechatVerify;

    /** 性别（0-未知，1-男，2-女） */
    @Min(0)
    @Max(2)
    private Integer gender;

    /** 身份证号 */
    @ValidString(value = StringEnum.ID_CARD_STRING, allowEmpty = true)
    private String idCard;

    @Null
    private Integer idCardVerify;

    /** 锁定标志（0-未锁定，1-已锁定） */
    @Null
    private Integer lockFlag;

    /** 删除标志（0-未删除，1-已删除） */
    @Null
    private Integer delFlag;

    public UserPO newUserPO() {
        UserPO userPO = new UserPO();
        userPO.setUserId(userId);
        userPO.setUsername(username);
        userPO.setRealName(realName);
        userPO.setNickname(nickname);
        userPO.setPassword(password);
        userPO.setAvatar(avatar);
        userPO.setBirthDate(birthDate);
        userPO.setPhone(phone);
        userPO.setEmail(email);
        userPO.setQq(qq);
        userPO.setWechat(wechat);
        userPO.setGender(gender);
        userPO.setIdCard(idCard);
        userPO.setLockFlag(lockFlag);
        userPO.setDelFlag(delFlag);
        userPO.setEmailVerify(emailVerify);
        userPO.setPhoneVerify(phoneVerify);
        userPO.setQqVerify(qqVerify);
        userPO.setWechatVerify(wechatVerify);
        userPO.setRealNameVerify(realNameVerify);
        userPO.setBirthDateVerify(birthDateVerify);
        userPO.setIdCardVerify(idCardVerify);
        return userPO;
    }

    public UserBaseInfo(UserPO userPO) {
        this.userId = userPO.getUserId();
        this.username = userPO.getUsername();
        this.realName = userPO.getRealName();
        this.nickname = userPO.getNickname();
        this.password = userPO.getPassword();
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
        this.emailVerify = userPO.getEmailVerify();
        this.phoneVerify = userPO.getPhoneVerify();
        this.qqVerify = userPO.getQqVerify();
        this.wechatVerify = userPO.getWechatVerify();
        this.realNameVerify = userPO.getRealNameVerify();
        this.birthDateVerify = userPO.getBirthDateVerify();
        this.idCardVerify = userPO.getIdCardVerify();
    }
}
