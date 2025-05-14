package com.mpc.wallet.app.api.common.mybatisplus.support.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充创建时间、更新时间
 */
@Component
@ConditionalOnClass(MetaObjectHandler.class)
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }

    @Override
    public <T,E extends T> MetaObjectHandler strictUpdateFill(MetaObject metaObject,String fieldName,Class<T> fieldType,E fieldVal) {
        metaObject.setValue(fieldName,fieldVal);
        return this;
    }
}