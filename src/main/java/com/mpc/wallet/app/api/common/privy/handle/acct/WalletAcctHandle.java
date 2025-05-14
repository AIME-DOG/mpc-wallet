package com.mpc.wallet.app.api.common.privy.handle.acct;

import com.mpc.wallet.app.api.common.privy.AcctHandle;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;
import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.bo.account.WalletAcctBo;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class WalletAcctHandle implements AcctHandle, InitializingBean {
    @Override
    public AccountBo handAcct(String value) {
        WalletAcctBo bo = JSONObject.parseObject(value , WalletAcctBo.class);
        if (Objects.isNull(bo)) {
            log.info("[解析Privy数据]解析Wallet账户返回空.value={}" , value);
            return null;
        }
        AccountBo result = new AccountBo();
        result.setType(bo.getType());
        result.setAddress(bo.getAddress());
        result.setChainType(bo.getChainType());
        result.setWalletClient(bo.getWalletClient());
        result.setWalletClientType(bo.getWalletClientType());
        return result;
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(LoginTypeEnum.WALLET , this);
    }
}