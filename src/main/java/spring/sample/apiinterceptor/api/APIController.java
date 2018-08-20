package spring.sample.apiinterceptor.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spring.sample.apiinterceptor.interceptor.ApiRequestInterceptor;

import javax.naming.AuthenticationException;
import java.util.Collections;

@RestController
public class APIController {


    @Bean
    public RestTemplate getInterceptorRestTemplate(ApiRequestInterceptor apiRequestInterceptor){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(apiRequestInterceptor));
        return restTemplate;
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/v1/interceptor/{num}",method = RequestMethod.GET)
    public String interceptor(@PathVariable String num){
        String url = "http://localhost:8888/v1/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",num);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        return res.getBody();
    }



    //認証用サンプルAPI
    @RequestMapping(value = "/v1/auth",method = RequestMethod.GET)
    public ResponseEntity<String> auth(@RequestHeader("Authorization") String authorization) throws AuthenticationException {
        if(!authorization.equals("1234")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}
