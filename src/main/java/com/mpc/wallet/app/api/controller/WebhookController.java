package com.mpc.wallet.app.api.controller;

import com.mpc.wallet.app.api.common.config.PrivyConfig;
import com.mpc.wallet.app.api.common.exception.AppServerException;
import com.mpc.wallet.app.api.common.privy.WebhookHandle;
import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.common.privy.enums.WebhookEventEnum;
import com.alibaba.fastjson2.JSONObject;
import com.svix.Webhook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/v1/privy")
@Tag(name = "Webhook")
public class WebhookController {

    @Resource
    private PrivyConfig privyConfig;


    /**
     * webhook
     */
    @PostMapping("/webhook/user")
    @Operation(summary = "User Webhook")
    ResponseEntity<Void> webhook2User(HttpServletRequest request , @RequestBody String payload) {
        String svixId = request.getHeader("svix-id");
        String svixTimestamp = request.getHeader("svix-timestamp");
        String svixSignature = request.getHeader("svix-signature");
        try {
            HashMap<String, List<String>> headerMap = new HashMap<>();
            headerMap.put("svix-id" , Collections.singletonList(svixId));
            headerMap.put("svix-timestamp" , Collections.singletonList(svixTimestamp));
            headerMap.put("svix-signature" , Collections.singletonList(svixSignature));
            Webhook webhook = new Webhook(privyConfig.getWebhookUserSigningKey());
            webhook.verify(payload , HttpHeaders.of(headerMap , (key , value) -> key.startsWith("svix"))); //验证签名
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            UserWebhookBo data = JSONObject.parseObject(payload , UserWebhookBo.class);
            WebhookEventEnum eventEnum = WebhookEventEnum.getEnumByCode(data.getType());
            if (Objects.isNull(eventEnum)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            //处理业务数据
            WebhookHandle webhookHandle = WebhookHandle.EXEC_MAP.get(eventEnum);
            if (Objects.isNull(webhookHandle)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            webhookHandle.handle(data);
        } catch (AppServerException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //处理业务数据
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

}