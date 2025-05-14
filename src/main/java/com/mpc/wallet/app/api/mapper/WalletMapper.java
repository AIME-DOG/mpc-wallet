package com.mpc.wallet.app.api.mapper;

import com.mpc.wallet.app.api.common.mybatisplus.support.annotations.MapperRepository;
import com.mpc.wallet.app.api.entity.Wallet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@MapperRepository
public interface WalletMapper extends BaseMapper<Wallet> {

}