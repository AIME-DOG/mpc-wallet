package com.mpc.wallet.app.api.common.privy.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class AccountBo implements Serializable {

    private String type;

    private String address;

    private String userName;

    private String acctId;

    private String chainType;

    private String walletClient;

    private String walletClientType;


}