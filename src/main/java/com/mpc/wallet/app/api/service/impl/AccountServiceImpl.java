package com.mpc.wallet.app.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mpc.wallet.app.api.common.config.PrivyConfig;
import com.mpc.wallet.app.api.common.enums.AppCode;
import com.mpc.wallet.app.api.common.exception.AppServerException;
import com.mpc.wallet.app.api.common.mybatisplus.entity.BaseIntEntity;
import com.mpc.wallet.app.api.common.privy.PrivyClient;
import com.mpc.wallet.app.api.common.privy.bo.AccountBo;
import com.mpc.wallet.app.api.common.privy.bo.UserBo;
import com.mpc.wallet.app.api.common.privy.bo.UserWebhookBo;
import com.mpc.wallet.app.api.common.privy.bo.WalletBo;
import com.mpc.wallet.app.api.common.util.PrivyUtil;
import com.mpc.wallet.app.api.entity.Account;
import com.mpc.wallet.app.api.entity.Wallet;
import com.mpc.wallet.app.api.mapper.AccountMapper;
import com.mpc.wallet.app.api.mapper.WalletMapper;
import com.mpc.wallet.app.api.model.req.AuthReq;
import com.mpc.wallet.app.api.model.res.AuthRes;
import com.mpc.wallet.app.api.service.IAccountService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private WalletMapper walletMapper;

    @Resource
    private PrivyClient privyClient;


    @Resource
    private PrivyConfig privyConfig;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthRes auth(AuthReq req) throws AppServerException {
        PrivyUtil.PrivyAuth privyAuth = PrivyUtil.validPrivyToken(privyConfig.getAppId() , privyConfig.getPublicKey() , privyConfig.getIssuer() , req.getAccessToken());
        if (privyAuth.getStatus().equalsIgnoreCase("fail")) {
            return AuthRes.builder().status("Fail").message(privyAuth.getMessage()).build();
        }
        Account account = saveAccount(privyAuth.getUserId());
        return AuthRes.builder().status("Success").accountId(account.getId()).privyUserId(privyAuth.getUserId()).build();
    }

    @Override
    public Account findByPrivyId(String privyId) throws AppServerException {
        return accountMapper.selectOne(
                Wrappers.lambdaQuery(Account.class).eq(Account::getPrivyUserId , privyId)
        );
    }

    private Account saveAccount(String privyUserId) {
        Account account = this.findByPrivyId(privyUserId);
        if (Objects.nonNull(account)) {
            return account;
        }
        UserBo userBo = privyClient.getInfoByUserId(privyUserId);
        if (Objects.isNull(userBo) || Objects.isNull(userBo.getAccount())) {
            throw new AppServerException(AppCode.ACCOUNT_NOT_EXISTS);
        }
        AccountBo acctBo = userBo.getAccount();
        account = new Account();
        account.setPrivyUserId(userBo.getId());
        account.setLinkedAcctType(acctBo.getType());
        account.setAddress(acctBo.getAddress());
        account.setChainType(acctBo.getChainType());
        account.setWalletClientType(acctBo.getWalletClientType());
        boolean rows = SqlHelper.retBool(accountMapper.insert(account));
        if (!rows) {
            throw new AppServerException(AppCode.SERVER_ERROR);
        }
        //保存地址信息
        saveWallet(account.getId() , account.getAddress() , userBo.getWallets());
        return account;
    }

    private void saveWallet(Long accountId , String address , List<WalletBo> walletBos) {
        if (CollUtil.isEmpty(walletBos)) {
            return;
        }
        walletBos
                .stream()
                .filter(e -> !e.getAddress().equals(address))
                .forEach(e -> {
                    Wallet wallet = new Wallet();
                    wallet.setAccountId(accountId);
                    wallet.setAddress(e.getAddress());
                    walletMapper.insert(wallet);
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveByWebhook(UserWebhookBo bo) throws AppServerException {
        //获取用户信息
        UserWebhookBo.UserInfo user = bo.getUser();
        //获取关联账户(第一条数据表示注册时的账户信息)
        UserWebhookBo.LinkedAccountInfo linkedAccountInfo = user.getLinkedAccounts().get(0);
        //创建用户
        Account account = new Account();
        account.setPrivyUserId(user.getId());
        account.setLinkedAcctType(linkedAccountInfo.getType());
        account.setAddress(linkedAccountInfo.getAddress());
        account.setChainType(linkedAccountInfo.getChainType());
        account.setWalletClientType(linkedAccountInfo.getWalletClientType());
        boolean rows = SqlHelper.retBool(accountMapper.insert(account));
        if (!rows) {
            throw new AppServerException(AppCode.SERVER_ERROR);
        }
    }

    @Override
    public Wallet findWalletByAccountIdAndAddress(Long accountId , String address) {
        return walletMapper.selectOne(
                Wrappers.lambdaQuery(Wallet.class)
                        .eq(Wallet::getAccountId , accountId)
                        .eq(Wallet::getAddress , address)
                        .orderByAsc(BaseIntEntity::getId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWallet(Wallet wallet) throws AppServerException {
        walletMapper.insert(wallet);
    }
}