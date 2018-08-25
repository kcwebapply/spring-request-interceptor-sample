package spring.sample.apiinterceptor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import spring.sample.apiinterceptor.interceptor.ApiRequestInterceptor;

import java.util.Collections;

@Configuration
public class ApiConfiguration {

    @Bean
    public RestTemplate getInterceptorRestTemplate(ApiRequestInterceptor apiRequestInterceptor){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(apiRequestInterceptor));
        return restTemplate;
    }
}
