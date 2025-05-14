package com.mpc.wallet.app.api.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: PrivyConfig
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "privy")
public class PrivyConfig {

    /**
     * 账号
     */
    private String account;

    /**
     * API host
     */
    private String host;

    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * Public key
     */
    private String publicKey;

    /**
     * Issur
     */
    private String issuer;

    /**
     * Webhook sign key
     */
    private String webhookUserSigningKey;
}