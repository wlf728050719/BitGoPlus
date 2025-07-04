package cn.bit.core.jsr303.enums;

import cn.bit.core.jsr303.constant.JSRConfigPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>字符串枚举</p>
 * Date:2025/05/07 20:25:02
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum StringEnum {
    ANY_STRING(JSRConfigPrefix.ANY_STRING_PREFIX), PHONE_STRING(JSRConfigPrefix.PHONE_STRING_PREFIX),
    EMAIL_STRING(JSRConfigPrefix.EMAIL_STRING_PREFIX), USERNAME_STRING(JSRConfigPrefix.USERNAME_STRING_PREFIX),
    PASSWORD_STRING(JSRConfigPrefix.PASSWORD_STRING_PREFIX), GENDER_STRING(JSRConfigPrefix.GENDER_STRING_PREFIX),
    QQ_STRING(JSRConfigPrefix.QQ_STRING_PREFIX), WECHAT_STRING(JSRConfigPrefix.WECHAT_STRING_PREFIX),
    URL_STRING(JSRConfigPrefix.URL_STRING_PREFIX), ID_CARD_STRING(JSRConfigPrefix.ID_CARD_STRING_PREFIX),;

    private final String prefix;
}
