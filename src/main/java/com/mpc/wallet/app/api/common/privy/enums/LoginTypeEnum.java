package com.mpc.wallet.app.api.common.privy.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 用户类型
 *
 * @description: UserTypeEnum
 */
@Getter
public enum LoginTypeEnum {

    EMAIL("email" , "邮箱"),

    WALLET("wallet" , "wallet"),

    TWITTER("twitter_oauth" , "Twitter"),

    GOOGLE("google_oauth" , "Google"),

    TELEGRAM("telegram" , "TG"),
    ;


    private final String code;

    private final String comment;

    LoginTypeEnum(String code , String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static LoginTypeEnum getEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return Arrays.stream(LoginTypeEnum.values()).filter(e -> e.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}