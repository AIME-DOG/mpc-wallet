package com.mpc.wallet.app.api.common.privy.handle.acct;

import com.mpc.wallet.app.api.common.privy.AcctHandle;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;
import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.bo.account.TelegramAcctBo;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class TelegramAcctHandle implements AcctHandle, InitializingBean {
    @Override
    public AccountBo handAcct(String value) {
        TelegramAcctBo bo = JSONObject.parseObject(value , TelegramAcctBo.class);
        if (Objects.isNull(bo)) {
            log.info("[解析Privy数据]解析TG账户返回空.value={}" , value);
            return null;
        }
        AccountBo result = new AccountBo();
        result.setType(bo.getType());
        result.setAcctId(bo.getTelegramUserId());
        result.setAddress(bo.getUsername());
        result.setUserName(bo.getFirstName());
        return result;
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(LoginTypeEnum.TELEGRAM , this);
    }
}