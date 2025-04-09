package cn.bit.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.bit.constant.DefaultValue;
import cn.bit.constant.Prefix;
import cn.bit.enums.FileEnum;
import cn.bit.enums.StringEnum;
import cn.bit.exception.SysException;
import cn.bit.pojo.FileLimit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jsr")
@Data
public class JSRConfig {
    private Map<String, String> regexMap;

    private Map<String, String> defaultRegexMap = new HashMap<>();

    private Map<String, FileLimit> fileLimitMap;

    private Map<String, FileLimit> defaultFileLimitMap = new HashMap<>();

    @PostConstruct
    public void init() {
        // 初始化字符串正则规则
        this.defaultRegexMap.put(Prefix.ANY_STRING_PREFIX, DefaultValue.DEFAULT_ANY_REGEX);
        this.defaultRegexMap.put(Prefix.PHONE_STRING_PREFIX, DefaultValue.DEFAULT_PHONE_REGEX);
        this.defaultRegexMap.put(Prefix.EMAIL_STRING_PREFIX, DefaultValue.DEFAULT_EMAIL_REGEX);

        // 初始化文件限制映射
        this.defaultFileLimitMap.put(Prefix.ANY_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_ANY_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_ANY_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(Prefix.IMAGE_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_IMAGE_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_IMAGE_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(Prefix.VIDEO_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_VIDEO_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_VIDEO_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(Prefix.AUDIO_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_AUDIO_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_AUDIO_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(Prefix.TEXT_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_TEXT_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_TEXT_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(Prefix.COMPRESSED_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_COMPRESSED_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_COMPRESSED_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
    }

    public String getRegex(StringEnum stringEnum) {
        if (regexMap == null || regexMap.get(stringEnum.getPrefix()) == null) {
            String defaultRegex = defaultRegexMap.get(stringEnum.getPrefix());
            if (defaultRegex != null) {
                log.warn("{} is null, use default regex", stringEnum.name());
                return defaultRegex;
            } else {
                throw new SysException("regex analyze error: " + stringEnum.name());
            }
        } else {
            return regexMap.get(stringEnum.getPrefix());
        }
    }

    public FileLimit getFileLimit(FileEnum fileEnum) {
        if (fileLimitMap == null || fileLimitMap.get(fileEnum.getPrefix()) == null) {
            FileLimit defaultFileLimit = defaultFileLimitMap.get(fileEnum.getPrefix());
            if (defaultFileLimit != null) {
                log.warn("{} is null, use default file limit", fileEnum.name());
                return defaultFileLimit;
            } else {
                throw new SysException("file limit analyze error: " + fileEnum.name());
            }
        } else {
            return fileLimitMap.get(fileEnum.getPrefix());
        }
    }
}
