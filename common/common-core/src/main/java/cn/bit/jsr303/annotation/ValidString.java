package cn.bit.jsr303.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.bit.jsr303.enums.StringEnum;
import cn.bit.jsr303.validator.StringValidator;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StringValidator.class)
public @interface ValidString {
    boolean useEnum() default true; // 默认优先使用枚举

    boolean allowEmpty() default false;

    StringEnum value() default StringEnum.ANY_STRING;

    String regex() default ".*";

    String message() default "invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
