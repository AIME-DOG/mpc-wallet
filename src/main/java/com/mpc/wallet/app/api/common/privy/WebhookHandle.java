package com.mpc.wallet.app.api.common.privy;

import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.common.privy.enums.WebhookEventEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 主要处理Privy的user相关的webhook
 *
 * @description: PrivyWebhookHandle
 */
public interface WebhookHandle {


    public static final Map<WebhookEventEnum, WebhookHandle> EXEC_MAP = new HashMap<>();

    /**
     * 处理
     *
     * @param data
     */
    void handle(UserWebhookBo data);
}
