package com.mpc.wallet.app.api.common.privy.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


@Data
public class LinkAcctBo implements Serializable {

    private String type;

    @JSONField(name = "wallet_client_type")
    private String walletClientType;
}