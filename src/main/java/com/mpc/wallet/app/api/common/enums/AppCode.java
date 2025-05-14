package com.mpc.wallet.app.api.common.enums;

import lombok.Getter;

/**
 * 返回常量枚举
 *
 * @author leo
 * @version 1.0.0
 */
@Getter
public enum AppCode {
    SUCCESS(0 , "成功"),
    WRONG_PARAMS(400 , "请求参数错误"),
    UNAUTHORIZED(401 , "未登录授权"),
    FORBIDDEN(403 , "禁止访问"),
    WRONG_REQUEST(405 , "请求类型错误"),
    SERVER_ERROR(500 , "服务异常"),
    DENIED(900 , "请求拒绝"),
    LOCK_FAILURE(998 , "正在执行,请勿频繁操作"),
    FAIL(999 , "操作失败"),
    TOKEN_ERROR(1000 , "登录失效"),
    LOGIN_FAILURE(1001 , "登录无效,请重新登录"),
    PARAMS_NULL(1010 , "参数为空"),
    PARAMS_ERROR(1011 , "参数错误"),
    ACCOUNT_NOT_EXISTS(1012 , "账号不存在");

    public static void main(String[] args) {
        for (AppCode e : AppCode.values()) {
            System.out.println(e.getCode() + "=" + e.getMsg());
        }
    }


    private final int code;

    private final String msg;

    AppCode(int code , String msg) {
        this.code = code;
        this.msg = msg;
    }

}
