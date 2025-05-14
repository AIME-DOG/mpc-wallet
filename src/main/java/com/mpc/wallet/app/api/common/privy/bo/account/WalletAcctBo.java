package com.mpc.wallet.app.api.common.privy.bo.account;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class WalletAcctBo implements Serializable {

    private String type;

    private String address;

    @JSONField(name = "chain_type")
    private String chainType;

    @JSONField(name = "wallet_client")
    private String walletClient;

    @JSONField(name = "wallet_client_type")
    private String walletClientType;

    @JSONField(name = "connector_type")
    private String connectorType;

    @JSONField(name = "verified_at")
    private Long verifiedAt;

    @JSONField(name = "first_verified_at")
    private Long firstVerifiedAt;

    @JSONField(name = "latest_verified_at")
    private Long latestVerifiedAt;
}