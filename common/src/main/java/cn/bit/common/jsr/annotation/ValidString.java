package cn.bit.common.jsr.annotation;

import cn.bit.common.jsr.enums.StringEnum;
import cn.bit.common.jsr.validator.StringValidator;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StringValidator.class)
public @interface ValidString {
    boolean useEnum() default true;//默认优先使用枚举

    StringEnum regexEnum() default StringEnum.ANY_STRING;

    String regex() default ".*";

    String message() default "invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

