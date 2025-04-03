package cn.bit.validator;

import cn.bit.annotation.ValidFile;
import cn.bit.config.JSRConfig;
import cn.bit.enums.FileEnum;
import cn.bit.pojo.FileLimit;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    @NonNull
    private JSRConfig jsrConfig;
    private long maxSize;
    private String[] allowedExtensions;
    private int maxFileNameLength;
    private Pattern fileNamePattern;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        boolean useEnum = constraintAnnotation.useEnum();
        if(useEnum) {
            FileEnum fileEnum = constraintAnnotation.fileEnum();
            if(fileEnum==FileEnum.ANY_FILE)
                log.warn("use any file");
            FileLimit fileLimit = jsrConfig.getFileLimit(fileEnum);
            maxSize = fileLimit.getMaxSize();
            allowedExtensions = fileLimit.getAllowedExtensions();
            maxFileNameLength = fileLimit.getMaxFileNameLength();
            fileNamePattern = Pattern.compile(fileLimit.getFileNameRegex());
        }
        else {
            maxSize = constraintAnnotation.maxSize();
            allowedExtensions = constraintAnnotation.allowedExtensions();
            maxFileNameLength = constraintAnnotation.maxFileNameLength();
            fileNamePattern = Pattern.compile(constraintAnnotation.fileNameRegex());
        }
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // 如果文件为空，认为是无效的
        }

        // 校验文件大小
        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than " + maxSize + " bytes")
                    .addConstraintViolation();
            return false;
        }

        // 校验文件扩展名
        if (allowedExtensions.length > 0) {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                return false;
            }
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("File extension must be one of " + Arrays.toString(allowedExtensions))
                        .addConstraintViolation();
                return false;
            }
        }

        // 校验文件名长度
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.length() > maxFileNameLength) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File name length must be less than " + maxFileNameLength + " characters")
                    .addConstraintViolation();
            return false;
        }

        // 校验文件名是否符合命名规范
        if (fileName != null && !fileNamePattern.matcher(fileName).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File name must match the pattern: " + fileNamePattern.pattern())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
