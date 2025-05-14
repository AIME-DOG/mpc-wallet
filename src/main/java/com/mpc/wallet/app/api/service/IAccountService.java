package com.mpc.wallet.app.api.service;

import com.mpc.wallet.app.api.common.exception.AppServerException;
import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.entity.Account;
import com.mpc.wallet.app.api.entity.Wallet;
import com.mpc.wallet.app.api.model.req.AuthReq;
import com.mpc.wallet.app.api.model.res.AuthRes;

/**
 * @description: AccountService
 */
public interface IAccountService {

    /**
     * Auth
     */
    AuthRes auth(AuthReq req) throws AppServerException;

    /**
     * Find account by privyUserId
     */
    Account findByPrivyId(String privyId) throws AppServerException;


    /**
     * Save user by webhook
     */
    void saveByWebhook(UserWebhookBo bo) throws AppServerException;


    /**
     * Find by accountId and wallet address
     */
    Wallet findWalletByAccountIdAndAddress(Long accountId , String address);

    /**
     * Save wallet
     */
    void saveWallet(Wallet wallet) throws AppServerException;

}
