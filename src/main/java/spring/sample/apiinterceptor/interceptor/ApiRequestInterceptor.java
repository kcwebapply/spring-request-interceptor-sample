package spring.sample.apiinterceptor.interceptor;


import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        List<String> token = new ArrayList<>();

        ClientHttpResponse response = null;
        response = execution.execute(request, body);
        if(response.getStatusCode()==HttpStatus.UNAUTHORIZED){//(1)
            System.out.println("認証に失敗したので、Authorizationヘッダーの値を更新し、再度アクセスします。");
            token.add("1234");
            request.getHeaders().put("Authorization", token);//(3)
            response = execution.execute(request, body);
        }
        return response;
    }

}
