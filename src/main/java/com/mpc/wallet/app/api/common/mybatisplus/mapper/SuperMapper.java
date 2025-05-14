package com.mpc.wallet.app.api.common.mybatisplus.mapper;

import com.mpc.wallet.app.api.common.mybatisplus.entity.SuperEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 所有Mapper的父类
 *
 * @param <T>
 */
public interface SuperMapper<T extends SuperEntity> extends BaseMapper<T> {
}