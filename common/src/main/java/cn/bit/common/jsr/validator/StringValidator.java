package cn.bit.common.jsr.validator;


import cn.bit.common.jsr.annotation.ValidString;
import cn.bit.common.jsr.config.JSRConfig;
import cn.bit.common.jsr.enums.StringEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class StringValidator implements ConstraintValidator<ValidString, String> {
    @NonNull
    private JSRConfig jsrConfig;
    private String regex;
    @Override
    public void initialize(ValidString constraintAnnotation) {
        boolean useEnum = constraintAnnotation.useEnum();
        if(useEnum) {
            StringEnum stringEnum = constraintAnnotation.regexEnum();
            if(stringEnum == StringEnum.ANY_STRING)
                log.warn("use any string");
            regex = jsrConfig.getRegex(stringEnum);
        }
        else
            regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(str).matches())
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(str+"不符合正则表达式:"+regex).addConstraintViolation();;
            return false;
        }
        return true;
    }
}
