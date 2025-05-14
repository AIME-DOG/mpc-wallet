package com.mpc.wallet.app.api.common.util;

import cn.hutool.core.collection.CollUtil;
import com.mpc.wallet.app.api.common.privy.AcctHandle;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;
import com.mpc.wallet.app.api.common.privy.bo.LinkAcctBo;
import com.mpc.wallet.app.api.common.privy.bo.UserBo;
import com.mpc.wallet.app.api.common.privy.bo.WalletBo;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @description: PrivyTokenUtil
 */
@Slf4j
public class PrivyUtil {

    public static PrivyAuth validPrivyToken(String appId , String key , String issuer , String token) {
        //获取请求头
        if (StringUtils.isBlank(token)) {
            return PrivyAuth.fail("Access token is null");
        }
        try {
            //解析token
            return PrivyUtil.analysisToken(appId , key , issuer , token);
        } catch (TokenExpiredException tee) {
            return PrivyAuth.fail(tee.getMessage());
        } catch (Exception ex) {
            return PrivyAuth.fail(ex.getMessage());
        }
    }


    private static PrivyAuth analysisToken(String appId , String key , String issuer , String token) throws Exception {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(token) || StringUtils.isBlank(issuer) || StringUtils.isBlank(appId)) {
            return PrivyAuth.fail("Privy config error.");
        }
        // 移除PEM格式的头部和尾部
        key = key.replace("-----BEGIN PUBLIC KEY-----" , "").replace("-----END PUBLIC KEY-----" , "").replaceAll("\\s" , "");
        // 解码Base64编码的公钥
        byte[] encodedKey = Base64.getDecoder().decode(key);
        // 创建ECPublicKey
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
        ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(keySpec);
        // 创建JWT Verifier
        Algorithm algorithm = Algorithm.ECDSA256(publicKey , null);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(appId).build();
        // 验证JWT
        DecodedJWT jwt = verifier.verify(token);
        return PrivyAuth.builder()
                .status("Success")
                .userId(jwt.getSubject())
                .expireAt(jwt.getExpiresAt())
                .appId(jwt.getKeyId())
                .build();
    }

    /**
     * 解析用户信息
     *
     * @param response
     * @return
     */
    public static UserBo analysisUserInfo(String response) {
        JSONObject data = JSONObject.parseObject(response);
        //申明返回结果
        UserBo userBo = new UserBo();
        userBo.setId(data.getString("id"));
        userBo.setCreatedAt(data.getLong("createdAt"));
        //获取linkAccount，数据
        JSONArray linkedAccounts = data.getJSONArray("linked_accounts");
        if (CollUtil.isEmpty(linkedAccounts)) {
            log.info("[解析Privy数据]没有账号信息.data={}" , data);
            return null;
        }
        //获取账户索引
        List<LinkAcctBo> linkAcctBos = JSONArray.parseArray(linkedAccounts.toJSONString() , LinkAcctBo.class);
        //遍历数据
        Integer index = 0;
        for (int i = 0; i < linkAcctBos.size(); i++) {
            LinkAcctBo item = linkAcctBos.get(i);
            if (!LoginTypeEnum.WALLET.getCode().equalsIgnoreCase(item.getType())) {
                index = i;
                break;
            }
            if (LoginTypeEnum.WALLET.getCode().equalsIgnoreCase(item.getType()) && StringUtils.isNotBlank(item.getWalletClientType()) && !"privy".equalsIgnoreCase(item.getWalletClientType())) {
                index = i;
                break;
            }
        }


        //将第一个数据作为账户处理
        JSONObject acctJson = linkedAccounts.getJSONObject(index);
        //处理账号
        LoginTypeEnum type = LoginTypeEnum.getEnumByCode(acctJson.getString("type"));
        if (Objects.isNull(type)) {
            log.info("[解析Privy数据]不支持的账户类型data={}" , data);
            return null;
        }
        AcctHandle acctHandle = AcctHandle.EXEC_MAP.get(type);
        if (Objects.isNull(acctHandle)) {
            log.info("[解析Privy数据]未获取到解析处理器={}" , data);
            return null;
        }
        userBo.setAccount(acctHandle.handAcct(acctJson.toJSONString()));
        if (CollUtil.isEmpty(linkedAccounts)) {
            return userBo;
        }
        List<WalletBo> wallets = new ArrayList<>();
        //处理钱包
        for (int i = 0; i < linkedAccounts.size(); i++) {
            if (i == index) {
                continue;
            }
            wallets.add(JSONObject.parseObject(linkedAccounts.getJSONObject(i).toJSONString() , WalletBo.class));
        }
        userBo.setWallets(wallets);
        return userBo;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PrivyAuth {

        private String status;

        private String message;

        /**
         * User IS
         */
        private String userId;

        /**
         * Expire Time
         */
        private Date expireAt;

        /**
         * APP ID
         */
        private String appId;


        public static PrivyAuth fail(String message) {
            return PrivyAuth.builder().status("Fail").message(message).build();
        }


    }
}