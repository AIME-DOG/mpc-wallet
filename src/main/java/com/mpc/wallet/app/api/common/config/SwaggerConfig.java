package com.mpc.wallet.app.api.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(Environment environment) {
        Profiles profiles = Profiles.of("dev" , "test");
        environment.acceptsProfiles(profiles);
        return new OpenAPI()
                .info(new Info()
                        .title("API Document")
                        .version("V1.0.0")
                        .description("API Document")
                        .license(new License().name("OpenAPI")));

    }

}
