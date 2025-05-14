package com.mpc.wallet.app.api.common.privy;


import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.enums.LoginTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: AcctHandle
 */
public interface AcctHandle {

    public static final Map<LoginTypeEnum, AcctHandle> EXEC_MAP = new HashMap<LoginTypeEnum, AcctHandle>();

    /**
     * 处理账号信息
     */
    AccountBo handAcct(String value);
}