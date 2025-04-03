package cn.bit.pojo.vo;


import cn.bit.constant.Code;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, Code.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, Code.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, Code.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, Code.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, Code.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, Code.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, Code.FAIL, msg);
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
