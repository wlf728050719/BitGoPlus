package cn.bit.core.handler;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.core.pojo.vo.R;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>全局异常处理类</p>
 * Date:2025/05/07 20:18:30
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常.
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Object> handleGlobalException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }
    /** 未认证异常处理 */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<Object> handleAuthenticationException(AuthenticationException e) {
        log.error("未认证异常信息 ex={}", e.getLocalizedMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }

    /**
     * 未授权异常处理
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error("未授权异常信息 ex={}", e.getLocalizedMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }

    /**
     * 服务器异常处理
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler(SysException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Object> handleSysException(SysException e) {
        log.error("服务器异常信息 ex={}", e.getMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }

    /**
     * 业务异常处理
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({BizException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleBizException(BizException e) {
        log.warn("业务处理异常,ex = {}", e.getMessage());
        return R.failed(e.getMessage());
    }

    /**
     * jsr303参数校验异常处理
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public R<Object> handleBodyValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append("\n");
        });
        log.warn("jsr303参数校验异常,ex = {}", errorMsg);
        return R.failed(errorMsg.toString());
    }

    /**
     * 表单参数绑定异常处理
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public R<Object> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append("\n");
        });
        log.warn("表单参数绑定异常,ex = {}", errorMsg);
        return R.failed(errorMsg.toString());
    }
}
