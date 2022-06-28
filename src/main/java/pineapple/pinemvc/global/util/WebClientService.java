package pineapple.pinemvc.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pineapple.pinemvc.global.dto.RequestDto;
import pineapple.pinemvc.global.error.ErrorCode;
import pineapple.pinemvc.global.error.exception.CustomException;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
//https://happycloud-lee.tistory.com/220 참고
public class WebClientService {

    private final WebClient webClient;

//    pine-zuul-url: "http://localhost:8000"
//    pine-euraka-url: "http://localhost:8001"
//    pine-cache-url: "http://localhost:8002"
//    pine-quant-url: "http://localhost:8101"

    @Value("${pineapple.pine-auth-url}") //TODO: 나중에 GATEWAYAPI로 변경
    private String authUrl;
    public WebClient getWebClient(String header) {
        return webClient.mutate()
                .defaultHeader("Authorization", header)
                .baseUrl(authUrl)
                .build();
    }

    public ResponseEntity requestRestCall(String header, RequestDto requestDto) {
        String uri = requestDto.getUrl();
        String method = requestDto.getMethod().toUpperCase(Locale.ROOT);

        ResponseEntity<String> response = null;

        try {
            WebClient client = this.getWebClient(header);

            if (method.equals("GET") || method.equals("DELETE")) {
                response = client.method(HttpMethod.valueOf(method))
                        .uri(uri)
                        .retrieve()
                        .onStatus(
                                status -> status.value() != 200,
                                r -> Mono.empty()
                        )
                        .toEntity(String.class)
                        .block();
            } else if (method.equals("POST") || method.equals("PATCH")) {
                response = client.method(HttpMethod.valueOf(method))
                        .uri(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .bodyValue(requestDto.getBody())
                        .retrieve()
                        .onStatus(
                                status -> status.value() != 200,
                                r -> Mono.empty()
                        )
                        .toEntity(String.class)
                        .block();
            }
            return response;

        } catch (Exception e) {
            //TODO
            String msg = e.getMessage();
            log.error("webClient Error : " + msg);
            throw new CustomException(ErrorCode.REQUEST_REJECTED);
        }
//        return null;
    }
}
