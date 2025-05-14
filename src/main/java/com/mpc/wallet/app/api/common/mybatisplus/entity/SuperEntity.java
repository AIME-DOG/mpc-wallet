package com.mpc.wallet.app.api.common.mybatisplus.entity;

import java.io.Serializable;


public abstract class SuperEntity<ID extends Serializable> {

    protected abstract Object getId();


}