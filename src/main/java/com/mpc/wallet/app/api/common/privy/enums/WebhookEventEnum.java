package com.mpc.wallet.app.api.common.privy.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 用户webhook事件
 *
 * @description: UserWebhookEventEnum
 */
@Getter
public enum WebhookEventEnum {

    USER_CREATED("user.created","创建用户"),

    WALLET_CREATED("user.wallet_created","创建钱包"),


    ;

    private final String code;

    private final String comment;

    WebhookEventEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public static WebhookEventEnum getEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return Arrays.stream(WebhookEventEnum.values()).filter(e->e.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
