package com.mpc.wallet.app.api.entity;

import com.mpc.wallet.app.api.common.mybatisplus.entity.BaseIntEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("account")
public class Account extends BaseIntEntity {

    /**
     * Privy平台用户ID
     */
    private String privyUserId;

    /**
     * 关联账号类型
     */
    private String linkedAcctType;

    /**
     * 地址
     */
    private String address;
    
    /**
     * 链类型(type=wallet时有效)
     */
    private String chainType;

    /**
     * 钱包客户端类型(type=wallet时有效)
     */
    private String walletClientType;

}