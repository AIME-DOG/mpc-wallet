package com.mpc.wallet.app.api.common.privy.handle.acct;

import com.mpc.wallet.app.api.common.privy.AcctHandle;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;
import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.bo.account.GoogleAcctBo;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GoogleAcctHandle implements AcctHandle, InitializingBean {
    @Override
    public AccountBo handAcct(String value) {
        GoogleAcctBo bo = JSONObject.parseObject(value , GoogleAcctBo.class);
        if (Objects.isNull(bo)) {
            log.info("[解析Privy数据]解析Google账户返回空.value={}" , value);
            return null;
        }
        AccountBo result = new AccountBo();
        result.setType(bo.getType());
        result.setAddress(bo.getEmail());
        result.setAcctId(bo.getSubject());
        result.setUserName(bo.getName());
        return result;
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(LoginTypeEnum.GOOGLE , this);
    }
}