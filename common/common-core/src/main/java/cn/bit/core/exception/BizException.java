package cn.bit.core.exception;

import lombok.NoArgsConstructor;

/**
 * <p>业务异常,用户未按规定执行业务时抛出</p>
 * Date:2025/05/07 12:13:50
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
