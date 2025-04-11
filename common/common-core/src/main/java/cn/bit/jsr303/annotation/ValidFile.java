package cn.bit.jsr303.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.bit.jsr303.enums.FileEnum;
import cn.bit.jsr303.validator.FileValidator;

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
