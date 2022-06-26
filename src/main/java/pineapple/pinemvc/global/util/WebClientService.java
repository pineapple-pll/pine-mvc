package pineapple.pinemvc.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
//https://happycloud-lee.tistory.com/220 참고
public class WebClientService {

    @Autowired
    private WebClient webClient;

//    pine-zuul-url: "http://localhost:8000"
//    pine-euraka-url: "http://localhost:8001"
//    pine-cache-url: "http://localhost:8002"
//    pine-quant-url: "http://localhost:8101"
    @Value("${pineapple.pine-auth-url}")
    private String authUrl;
}
