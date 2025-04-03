package cn.bit.enums;

import cn.bit.constant.Prefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileEnum {
    ANY_FILE(Prefix.ANY_FILE_PREFIX), // 任意文件
    IMAGE_FILE(Prefix.IMAGE_FILE_PREFIX), // 图片文件
    VIDEO_FILE(Prefix.VIDEO_FILE_PREFIX), // 视频文件
    AUDIO_FILE(Prefix.AUDIO_FILE_PREFIX), // 音频文件
    TEXT_FILE(Prefix.TEXT_FILE_PREFIX), // 文本文件
    COMPRESSED_FILE(Prefix.COMPRESSED_FILE_PREFIX); // 压缩包文件

    private final String prefix; // 文件类型前缀
}
