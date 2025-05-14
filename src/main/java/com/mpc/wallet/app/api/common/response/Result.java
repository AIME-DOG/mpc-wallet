package com.mpc.wallet.app.api.common.response;

import com.mpc.wallet.app.api.common.enums.AppCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回组装
 *
 * @author leo
 * @version 1.0.0
 */
@Data
public class Result<T> implements Serializable {

    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isI18n;

    private Integer code;

    @JsonInclude()
    private T data;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private Object[] params;

    public Result( ) {
    }

    public Result(int code , String msg , T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(int code , String msg , T data , Boolean isI18n) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.isI18n = isI18n;
    }

    public static <T> Result<T> success( ) {
        return new Result<>(AppCode.SUCCESS.getCode() , AppCode.SUCCESS.getMsg() , null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(AppCode.SUCCESS.getCode() , AppCode.SUCCESS.getMsg() , data);
    }

    public static <T> Result<T> fail( ) {
        return Result.fail(AppCode.FAIL);
    }

    public static <T> Result<T> fail(int code , String msg , T data) {
        return new Result<>(code , msg , data);
    }

    public static <T> Result<T> fail(int code , String msg , T data , Boolean isI18n) {
        return new Result<>(code , msg , data , isI18n);
    }

    public static <T> Result<T> fail(AppCode code) {
        return new Result<>(code.getCode() , code.getMsg() , null);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(AppCode.FAIL , msg);
    }

    public static <T> Result<T> fail(AppCode resultCode , String msg) {
        Result<T> result = new Result<>();
        result.code = resultCode.getCode();
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> fail(AppCode code , T data) {
        return new Result<>(code.getCode() , code.getMsg() , data);
    }

    public static <T> Result<T> bool(boolean b) {
        return b ? Result.success() : Result.fail();
    }

    @JsonIgnore
    public boolean isSuccess( ) {
        return AppCode.SUCCESS.getCode() == this.code;
    }

    @JsonIgnore
    public boolean isFail( ) {
        return AppCode.SUCCESS.getCode() != this.code;
    }
}
