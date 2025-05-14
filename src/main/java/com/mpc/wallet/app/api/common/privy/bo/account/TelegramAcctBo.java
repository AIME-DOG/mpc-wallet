package com.mpc.wallet.app.api.common.privy.bo.account;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


@Data
public class TelegramAcctBo implements Serializable {

    private String type;

    @JSONField(name = "telegram_user_id")
    private String telegramUserId;

    @JSONField(name = "first_name")
    private String firstName;

    @JSONField(name = "last_name")
    private String lastName;

    private String username;

    @JSONField(name = "photo_url")
    private String photoUrl;

    @JSONField(name = "verified_at")
    private Long verifiedAt;

    @JSONField(name = "first_verified_at")
    private Long firstVerifiedAt;

    @JSONField(name = "latest_verified_at")
    private Long latestVerifiedAt;


}