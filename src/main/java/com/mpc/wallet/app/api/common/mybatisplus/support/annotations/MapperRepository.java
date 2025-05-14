package com.mpc.wallet.app.api.common.mybatisplus.support.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 超级Mapper注解，MapperScan只扫描该注解的Mapper
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MapperRepository {
}