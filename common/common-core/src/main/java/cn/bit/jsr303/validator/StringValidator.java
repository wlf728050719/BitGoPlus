package cn.bit.jsr303.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import cn.bit.jsr303.annotation.ValidString;
import cn.bit.jsr303.config.JSRConfig;
import cn.bit.jsr303.enums.StringEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StringValidator implements ConstraintValidator<ValidString, String> {
    @NonNull
    private JSRConfig jsrConfig;
    private String regex;
    private boolean allowEmpty;

    @Override
    public void initialize(ValidString constraintAnnotation) {
        boolean useEnum = constraintAnnotation.useEnum();
        allowEmpty = constraintAnnotation.allowEmpty();
        if (useEnum) {
            StringEnum stringEnum = constraintAnnotation.value();
            if (stringEnum == StringEnum.ANY_STRING) {
                log.warn("use any string");
            }
            regex = jsrConfig.getRegex(stringEnum);
        } else {
            regex = constraintAnnotation.regex();
        }
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (str == null || str.isEmpty()) {
            return allowEmpty;
        }
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(str).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(str + "不符合正则表达式:" + regex).addConstraintViolation();
            return false;
        }
        return true;
    }
}
