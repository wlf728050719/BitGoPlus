package cn.bit.jsr303.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.bit.exception.SysException;
import cn.bit.jsr303.constant.DefaultValue;
import cn.bit.jsr303.constant.JSRConfigPrefix;
import cn.bit.jsr303.enums.FileEnum;
import cn.bit.jsr303.enums.StringEnum;
import cn.bit.jsr303.pojo.FileLimit;
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
        this.defaultRegexMap.put(JSRConfigPrefix.ANY_STRING_PREFIX, DefaultValue.DEFAULT_ANY_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.PHONE_STRING_PREFIX, DefaultValue.DEFAULT_PHONE_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.EMAIL_STRING_PREFIX, DefaultValue.DEFAULT_EMAIL_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.USERNAME_STRING_PREFIX, DefaultValue.DEFAULT_USERNAME_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.PASSWORD_STRING_PREFIX, DefaultValue.DEFAULT_PASSWORD_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.URL_STRING_PREFIX, DefaultValue.DEFAULT_URL_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.ID_CARD_STRING_PREFIX, DefaultValue.DEFAULT_ID_CARD_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.QQ_STRING_PREFIX, DefaultValue.DEFAULT_QQ_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.WECHAT_STRING_PREFIX, DefaultValue.DEFAULT_WECHAT_REGEX);
        this.defaultRegexMap.put(JSRConfigPrefix.GENDER_STRING_PREFIX, DefaultValue.DEFAULT_GENDER_REGEX);

        // 初始化文件限制映射
        this.defaultFileLimitMap.put(JSRConfigPrefix.ANY_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_ANY_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_ANY_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(JSRConfigPrefix.IMAGE_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_IMAGE_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_IMAGE_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(JSRConfigPrefix.VIDEO_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_VIDEO_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_VIDEO_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(JSRConfigPrefix.AUDIO_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_AUDIO_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_AUDIO_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(JSRConfigPrefix.TEXT_FILE_PREFIX,
            new FileLimit(DefaultValue.DEFAULT_TEXT_FILE_MAX_SIZE, DefaultValue.DEFAULT_FILE_MAX_NAME_LENGTH,
                DefaultValue.DEFAULT_TEXT_FILE_EXTENSIONS, DefaultValue.DEFAULT_FILE_NAME_PATTERN));
        this.defaultFileLimitMap.put(JSRConfigPrefix.COMPRESSED_FILE_PREFIX,
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
