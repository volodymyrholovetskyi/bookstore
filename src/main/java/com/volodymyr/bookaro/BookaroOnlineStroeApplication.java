package com.volodymyr.bookaro;

import com.volodymyr.bookaro.order.application.OrdersProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableConfigurationProperties(OrdersProperties.class)
@SpringBootApplication
public class BookaroOnlineStroeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookaroOnlineStroeApplication.class, args);
    }


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }
}
