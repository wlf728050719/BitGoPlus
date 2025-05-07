package cn.bit.core.jsr303.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.bit.core.jsr303.enums.StringEnum;
import cn.bit.core.jsr303.validator.StringValidator;

/**
 * <p>验证字符串是否符合规范</p>
 * Date:2025/05/07 20:18:56
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
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
