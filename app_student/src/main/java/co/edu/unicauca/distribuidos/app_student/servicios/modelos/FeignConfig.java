package co.edu.unicauca.distribuidos.app_student.servicios.modelos;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class FeignConfig {
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(Duration.ofSeconds(5), Duration.ofSeconds(5), false);
    }
}
