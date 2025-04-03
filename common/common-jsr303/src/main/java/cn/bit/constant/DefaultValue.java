package cn.bit.constant;

import lombok.Getter;

@Getter
public class DefaultValue {
    //String
    public static final String DEFAULT_ANY_REGEX = ".*";
    public static final String DEFAULT_PHONE_REGEX = "^1[3-9]\\d{9}$";
    public static final String DEFAULT_EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    //File
    public static final Long DEFAULT_ANY_FILE_MAX_SIZE = 5L*1024*1024*1024; //5G
    public static final Long DEFAULT_IMAGE_FILE_MAX_SIZE = 50L * 1024 * 1024; //50M
    public static final Long DEFAULT_VIDEO_FILE_MAX_SIZE = 1024L * 1024 * 1024; //1G
    public static final Long DEFAULT_AUDIO_FILE_MAX_SIZE = 100L * 1024 * 1024; //100M
    public static final Long DEFAULT_TEXT_FILE_MAX_SIZE = 50L * 1024 * 1024; //50M
    public static final Long DEFAULT_COMPRESSED_FILE_MAX_SIZE = 1024L * 1024 * 1024; //1G

    public static final String[] DEFAULT_ANY_FILE_EXTENSIONS = new String[]{};
    public static final String[] DEFAULT_IMAGE_FILE_EXTENSIONS = new String[]{"jpg", "jpeg", "png", "gif"};
    public static final String[] DEFAULT_VIDEO_FILE_EXTENSIONS =  new String[]{"mp4", "avi", "mkv", "mov"};
    public static final String[] DEFAULT_AUDIO_FILE_EXTENSIONS = new String[]{"mp3", "wav", "aac"};
    public static final String[] DEFAULT_TEXT_FILE_EXTENSIONS = new String[]{"txt", "pdf", "docx", "doc"};
    public static final String[] DEFAULT_COMPRESSED_FILE_EXTENSIONS = new String[]{"zip", "rar", "7z"};

    public static final Integer DEFAULT_FILE_MAX_NAME_LENGTH = 255;
    public static final String DEFAULT_FILE_NAME_PATTERN = "^[a-zA-Z0-9_.-]+$";


}
