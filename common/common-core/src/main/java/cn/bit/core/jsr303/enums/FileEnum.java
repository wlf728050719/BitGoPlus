package cn.bit.core.jsr303.enums;

import cn.bit.core.jsr303.constant.JSRConfigPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileEnum {
    ANY_FILE(JSRConfigPrefix.ANY_FILE_PREFIX), // 任意文件
    IMAGE_FILE(JSRConfigPrefix.IMAGE_FILE_PREFIX), // 图片文件
    VIDEO_FILE(JSRConfigPrefix.VIDEO_FILE_PREFIX), // 视频文件
    AUDIO_FILE(JSRConfigPrefix.AUDIO_FILE_PREFIX), // 音频文件
    TEXT_FILE(JSRConfigPrefix.TEXT_FILE_PREFIX), // 文本文件
    COMPRESSED_FILE(JSRConfigPrefix.COMPRESSED_FILE_PREFIX); // 压缩包文件

    private final String prefix; // 文件类型前缀
}
