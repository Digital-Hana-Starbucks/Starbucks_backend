package com.hanaro.starbucks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("Starbucks kiosk API")
                        .description("디지털 하나로 3기 프로젝트 #1 - 스타벅스 키오스크")
                        .version("v0.0.1"));
    }
}
