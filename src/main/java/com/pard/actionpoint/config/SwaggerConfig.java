package com.pard.actionpoint.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("Action Point API")
                .description("파드 5기 롱커톤 불만있냐 팀의 Action Point API 스웨거입니다.")
                .version("1.0.0");
    }

    // http://localhost:8080/swagger-ui/index.html
}