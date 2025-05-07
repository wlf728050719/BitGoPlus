package cn.bit.core.jsr303.constant;

import lombok.Getter;

/**
 * <p>jsr303默认配置值</p>
 * Date:2025/05/07 20:23:51
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class DefaultValue {
    // String
    public static final String DEFAULT_ANY_REGEX = ".*";
    public static final String DEFAULT_PHONE_REGEX = "^1[3-9]\\d{9}$";
    public static final String DEFAULT_EMAIL_REGEX = "^(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    public static final String DEFAULT_USERNAME_REGEX = "^[a-zA-Z0-9_\\-]{4,20}$";
    public static final String DEFAULT_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,20}$";
    public static final String DEFAULT_QQ_REGEX = "^[1-9][0-9]{4,10}$";
    public static final String DEFAULT_WECHAT_REGEX = "^[a-zA-Z][-_a-zA-Z0-9]{5,19}$";
    public static final String DEFAULT_URL_REGEX = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
    public static final String DEFAULT_GENDER_REGEX = "^[男女]$";
    public static final String DEFAULT_ID_CARD_REGEX =
        "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$";
    // File
    public static final Long DEFAULT_ANY_FILE_MAX_SIZE = 5L * 1024 * 1024 * 1024; // 5G
    public static final Long DEFAULT_IMAGE_FILE_MAX_SIZE = 50L * 1024 * 1024; // 50M
    public static final Long DEFAULT_VIDEO_FILE_MAX_SIZE = 1024L * 1024 * 1024; // 1G
    public static final Long DEFAULT_AUDIO_FILE_MAX_SIZE = 100L * 1024 * 1024; // 100M
    public static final Long DEFAULT_TEXT_FILE_MAX_SIZE = 50L * 1024 * 1024; // 50M
    public static final Long DEFAULT_COMPRESSED_FILE_MAX_SIZE = 1024L * 1024 * 1024; // 1G

    public static final String[] DEFAULT_ANY_FILE_EXTENSIONS = new String[] {};
    public static final String[] DEFAULT_IMAGE_FILE_EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    public static final String[] DEFAULT_VIDEO_FILE_EXTENSIONS = new String[] {"mp4", "avi", "mkv", "mov"};
    public static final String[] DEFAULT_AUDIO_FILE_EXTENSIONS = new String[] {"mp3", "wav", "aac"};
    public static final String[] DEFAULT_TEXT_FILE_EXTENSIONS = new String[] {"txt", "pdf", "docx", "doc"};
    public static final String[] DEFAULT_COMPRESSED_FILE_EXTENSIONS = new String[] {"zip", "rar", "7z"};

    public static final Integer DEFAULT_FILE_MAX_NAME_LENGTH = 255;
    public static final String DEFAULT_FILE_NAME_PATTERN = "^[a-zA-Z0-9_.-]+$";

}
