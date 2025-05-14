package com.mpc.wallet.app.api.common.privy.handle.webhook;

import com.mpc.wallet.app.api.common.enums.AppCode;
import com.mpc.wallet.app.api.common.exception.AppServerException;
import com.mpc.wallet.app.api.common.privy.WebhookHandle;
import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.common.privy.enums.WebhookEventEnum;
import com.mpc.wallet.app.api.entity.Account;
import com.mpc.wallet.app.api.entity.Wallet;
import com.mpc.wallet.app.api.service.IAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description: WalletCreatedHandle
 */
@Service
@Slf4j
public class WalletCreatedHandle implements WebhookHandle, InitializingBean {

    @Resource
    private IAccountService accountService;


    @Override
    public void handle(UserWebhookBo data) {
        String id = data.getUser().getId();
        UserWebhookBo.WalletInfo walletInfo = data.getWallet();

        //根据用户ID查询用户
        Account account = accountService.findByPrivyId(id);
        if (Objects.isNull(account)) {
            throw new AppServerException(AppCode.FAIL);
        }
        //查询钱包是否存在
        Wallet wallet = accountService.findWalletByAccountIdAndAddress(account.getId() , walletInfo.getAddress());
        if (Objects.nonNull(wallet)) {
            return;
        }
        //创建钱包
        wallet = new Wallet();
        wallet.setAccountId(account.getId());
        wallet.setAddress(walletInfo.getAddress());
        accountService.saveWallet(wallet);
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(WebhookEventEnum.WALLET_CREATED , this);
    }
}