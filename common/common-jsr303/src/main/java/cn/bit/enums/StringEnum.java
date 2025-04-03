package cn.bit.enums;

import cn.bit.constant.Prefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StringEnum {
    ANY_STRING(Prefix.ANY_STRING_PREFIX),
    PHONE_STRING(Prefix.PHONE_STRING_PREFIX),
    EMAIL_STRING(Prefix.EMAIL_STRING_PREFIX),;
    private final String prefix;
}
