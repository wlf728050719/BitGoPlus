package cn.bit.pojo.dto;

import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.pojo.po.UserPO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
public class UserBaseInfo {
    /** 用户名 */
    @ValidString(StringEnum.USERNAME_STRING)
    private String username;

    /** 真实姓名 */
    private String realName;

    /** 昵称 */
    private String nickname;

    /** 密码明文 */
    @ValidString(StringEnum.PASSWORD_STRING)
    private String password;

    /** 头像URL */
    private String avatar;

    /** 出生日期 */
    private Date birthDate;

    /** 手机号 */
    @ValidString(StringEnum.PHONE_STRING)
    private String phone;

    /** 邮箱 */
    @ValidString(value = StringEnum.EMAIL_STRING, allowEmpty = true)
    private String email;

    /** QQ号 */
    @ValidString(value = StringEnum.QQ_STRING, allowEmpty = true)
    private String qq;

    /** 微信号 */
    @ValidString(value = StringEnum.WECHAT_STRING, allowEmpty = true)
    private String wechat;

    /** 性别（0-未知，1-男，2-女） */
    @Min(0)
    @Max(2)
    private Integer gender;

    /** 身份证号 */
    @ValidString(value = StringEnum.ID_CARD_STRING, allowEmpty = true)
    private String idCard;

    public UserPO newUserPO() {
        UserPO userPO = new UserPO();
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
        return userPO;
    }
}
