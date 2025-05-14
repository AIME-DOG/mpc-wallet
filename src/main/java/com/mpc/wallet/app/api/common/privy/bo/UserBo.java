package com.mpc.wallet.app.api.common.privy.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBo implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 创建时间
     */
    private Long createdAt;

    /**
     * 账户信息
     */
    private AccountBo account;

    /**
     * 钱包集合
     */
    private List<WalletBo> wallets;

}