package cn.bit.core.handler;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
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

    /**
     * AccessDeniedException
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error("拒绝授权异常信息 ex={}", e.getLocalizedMessage(), e);
        return R.failed(e.getLocalizedMessage());
    }

    /**
     * 服务器异常
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
     * 业务处理类
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({BizException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> bizExceptionHandler(BizException e) {
        log.warn("业务处理异常,ex = {}", e.getMessage());
        return R.failed(e.getMessage());
    }

    /**
     * validation Exception
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleBodyValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(" ");
        });
        log.warn("参数绑定异常,ex = {}", errorMsg);
        return R.failed(errorMsg.toString());
    }

    /**
     * validation Exception (以form-data形式传参)
     * 
     * @param e the e
     * @return R
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMsg = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append("\n");
        });
        log.warn("参数绑定异常(form-data),ex = {}", errorMsg);
        return R.failed(errorMsg.toString());
    }
}
