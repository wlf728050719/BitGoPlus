package cn.bit.core.jsr303.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileLimit {
    private Long maxSize;
    private Integer maxFileNameLength;
    private String[] allowedExtensions;
    private String fileNameRegex;
}
