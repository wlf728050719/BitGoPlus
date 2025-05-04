package cn.bit.core.jsr303.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.bit.core.jsr303.enums.FileEnum;
import cn.bit.core.jsr303.validator.FileValidator;

/**
 * <p>验证文件类型，大小是否符合要求，只设置枚举时使用对应枚举文件类型配置大小限制以及后缀名限制</p>
 * Date:2025/05/04 14:59:42
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {

    String message() default "Invalid file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean useEnum() default true; // 默认优先使用枚举

    FileEnum fileEnum() default FileEnum.ANY_FILE;

    long maxSize() default 5L * 1024 * 1024 * 1024; // 文件最大字节大小

    String[] allowedExtensions() default {}; // 允许的文件扩展名

    int maxFileNameLength() default 255; // 文件名的最大长度

    String fileNameRegex() default "^[a-zA-Z0-9_.-]+$"; // 文件名的正则表达式规则
}
