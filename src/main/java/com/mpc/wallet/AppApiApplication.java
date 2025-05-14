package com.mpc.wallet;

import cn.hutool.core.date.DateUtil;
import com.mpc.wallet.app.api.common.mybatisplus.support.annotations.MapperRepository;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Slf4j
@RefreshScope
@SpringBootApplication
@MapperScan(annotationClass = MapperRepository.class)
public class AppApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(AppApiApplication.class , args);
        log.info("服务启动成功.{}" , DateUtil.date());
    }


}
