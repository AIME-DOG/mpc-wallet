package com.mpc.wallet.app.api.common.privy.handle.webhook;

import com.mpc.wallet.app.api.common.privy.WebhookHandle;
import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.common.privy.enums.WebhookEventEnum;
import com.mpc.wallet.app.api.entity.Account;
import com.mpc.wallet.app.api.service.IAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @description: UserCreatedHandle
 */
@Slf4j
@Service
public class UserCreatedHandle implements WebhookHandle, InitializingBean {


    @Resource
    private IAccountService accountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handle(UserWebhookBo data) {
        String id = data.getUser().getId();
        Account account = accountService.findByPrivyId(id);
        if (Objects.nonNull(account)) {
            return;
        }
        accountService.saveByWebhook(data);
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(WebhookEventEnum.USER_CREATED , this);
    }
}