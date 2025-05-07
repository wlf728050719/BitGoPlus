package cn.bit.core.exception;

import lombok.NoArgsConstructor;

/**
 * <p>系统异常，系统组件错误时抛出</p>
 * Date:2025/05/07 12:16:50
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
public class SysException extends RuntimeException {

    public SysException(String message) {
        super(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
