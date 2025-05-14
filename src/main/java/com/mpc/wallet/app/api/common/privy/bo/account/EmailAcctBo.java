package com.mpc.wallet.app.api.common.privy.bo.account;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmailAcctBo implements Serializable {

    private String type;

    private String address;

    @JSONField(name = "verified_at")
    private Long verifiedAt;

    @JSONField(name = "first_verified_at")
    private Long firstVerifiedAt;

    @JSONField(name = "latest_verified_at")
    private Long latestVerifiedAt;
}