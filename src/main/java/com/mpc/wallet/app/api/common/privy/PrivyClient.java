package com.mpc.wallet.app.api.common.privy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.mpc.wallet.app.api.common.config.PrivyConfig;
import com.mpc.wallet.app.api.common.privy.bo.UserBo;
import com.mpc.wallet.app.api.common.util.PrivyUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PrivyClient {

    @Resource
    private PrivyConfig privyConfig;

    public UserBo getInfoByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        String url = String.format("https://auth.privy.io/api/v1/users/%s" , userId);
        String response = get(url);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return PrivyUtil.analysisUserInfo(response);
    }


    /**
     * Get请求
     */
    private String get(String url) {
        HttpRequest request = null;
        try {
            request = HttpUtil.createGet(url);
            request.basicAuth(privyConfig.getAccount() , privyConfig.getAppSecret());
            request.header("privy-app-id" , privyConfig.getAppId());
            request.header("Content-Type" , "application/json");
        } catch (Exception exception) {
            return null;
        }
        HttpResponse response = request.execute();
        if (response.getStatus() != HttpStatus.HTTP_OK) {
            return null;
        }
        return response.body();
    }
}