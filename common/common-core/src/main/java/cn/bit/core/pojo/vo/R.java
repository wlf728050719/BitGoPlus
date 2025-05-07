package cn.bit.core.pojo.vo;

import java.io.Serializable;

import cn.bit.core.constant.ResponseCode;
import lombok.Data;

/**
 * <p>响应类</p>
 * Date:2025/05/07 20:29:40
 *
 * @author <a href="mailto:18086270070@163.com">Luofei Wang</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:JavadocType")
@Data
public class R<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, ResponseCode.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, ResponseCode.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, ResponseCode.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, ResponseCode.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ResponseCode.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, ResponseCode.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, ResponseCode.FAIL, msg);
    }

    public static <T> R<T> failed(T data, int code, String msg) {
        return restResult(data, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
