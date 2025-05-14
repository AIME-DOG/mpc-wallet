package com.mpc.wallet.app.api.common.privy.handle.acct;

import com.mpc.wallet.app.api.common.privy.AcctHandle;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;
import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.bo.account.TwitterAcctBo;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class TwitterAcctHandle implements AcctHandle, InitializingBean {
    @Override
    public AccountBo handAcct(String value) {
        TwitterAcctBo bo = JSONObject.parseObject(value , TwitterAcctBo.class);
        if (Objects.isNull(bo)) {
            log.info("[解析Privy数据]解析Twitter账户返回空.value={}" , value);
            return null;
        }
        AccountBo result = new AccountBo();
        result.setType(bo.getType());
        result.setAcctId(bo.getSubject());
        result.setAddress(bo.getUsername());
        result.setUserName(bo.getName());
        return result;
    }

    @Override
    public void afterPropertiesSet( ) throws Exception {
        EXEC_MAP.put(LoginTypeEnum.TWITTER , this);
    }
}