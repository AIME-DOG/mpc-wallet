package com.mpc.wallet.app.api.common.privy.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Privy user webhook
 * @description: PrivateUserWebhookData
 */
@Data
public class UserWebhookBo implements Serializable {

    /**
     * 类型
     * user.created 创建用户
     * user.wallet_created 创建钱包
     */
    private String type;

    /**
     * 用户信息
     */
    private UserInfo user;

    /**
     * 账号信息
     */
    private AccountInfo account;

    /**
     * 钱包信息
     */
    private WalletInfo wallet;


    /**
     * 用户信息
     */
    @Data
    public static class UserInfo implements Serializable {

        /**
         * 创建时间
         */
        @JSONField(name = "created_at")
        private Long createAt;

        /**
         * 是否接受条款
         */
        @JSONField(name = "has_accepted_terms")
        private Boolean hasAcceptedTerms;

        /**
         * ID
         */
        private String id;

        /**
         * 是否游客
         */
        @JSONField(name = "is_guest")
        private Boolean isGuest;

        /**
         * 关联账户
         */
        @JSONField(name = "linked_accounts")
        private List<LinkedAccountInfo> linkedAccounts;


    }

    @Data
    public static class LinkedAccountInfo implements Serializable {

        /**
         * ID
         */
        private String id;

        /**
         * 地址
         */
        private String address;

        /**
         * 链ID
         */
        @JSONField(name = "chain_id")
        private String chainId;

        /**
         * 链类型
         */
        @JSONField(name = "chain_type")
        private String chainType;

        /**
         * 连接器类型
         */
        @JSONField(name = "connector_type")
        private String connectorType;

        /**
         * 是否委托
         */
        private Boolean delegated;

        /**
         * 首次验证时间
         */
        @JSONField(name = "first_verified_at")
        private Long firstVerifiedAt;

        /**
         * 是否导入
         */
        private Boolean imported;

        /**
         * 最后验证时间
         */
        @JSONField(name = "latest_verified_at")
        private Long latestVerifiedAt;

        /**
         * 公钥
         */
        private String publicKey;


        /**
         * 恢复方法
         */
        @JSONField(name = "recovery_method")
        private String recoveryMethod;

        /**
         * 类型
         */
        private String type;


        /**
         * 验证时间
         */
        @JSONField(name = "verified_at")
        private Long verifiedAt;

        /**
         * 钱包客户端
         */
        @JSONField(name = "wallet_client")
        private String walletClient;

        /**
         * 钱包客户端类型
         */
        @JSONField(name = "wallet_client_type")
        private String walletClientType;

        /**
         * 钱包索引
         */
        private Integer walletIndex;


    }

    @Data
    public static class AccountInfo implements Serializable {
        /**
         * 地址
         */
        private String address;

        /**
         * 链ID
         */
        @JSONField(name = "chain_id")
        private String chainId;

        /**
         * 链类型
         */
        @JSONField(name = "chain_type")
        private String chainType;

        /**
         * 连接器类型
         */
        @JSONField(name = "connector_type")
        private String connectorType;


        /**
         * 首次验证时间
         */
        @JSONField(name = "first_verified_at")
        private Long firstVerifiedAt;

        /**
         * 最后验证时间
         */
        @JSONField(name = "latest_verified_at")
        private Long latestVerifiedAt;

        /**
         * 类型
         */
        private String type;

        /**
         * 验证时间
         */
        @JSONField(name = "verified_at")
        private Long verifiedAt;

        /**
         * 钱包客户端
         */
        @JSONField(name = "wallet_client")
        private String walletClient;

        /**
         * 钱包客户端类型
         */
        @JSONField(name = "wallet_client_type")
        private String walletClientType;


    }

    @Data
    public static class WalletInfo implements Serializable {

        /**
         * 钱包地址
         */
        private String address;

        /**
         * 链类型
         */
        @JSONField(name = "chain_type")
        private String chainType;

        /**
         * 类型
         */
        private String type;
    }

}