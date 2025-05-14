package com.mpc.wallet.app.api.mapper;

import com.mpc.wallet.app.api.common.mybatisplus.support.annotations.MapperRepository;
import com.mpc.wallet.app.api.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @description: AccountMapper
 */
@MapperRepository
public interface AccountMapper extends BaseMapper<Account> {
}