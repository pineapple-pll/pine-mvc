package pineapple.pinemvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pineapple.pinemvc.dto.Member;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/login")
    public String login() {
        return "page-login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "page-register";
    }

    @PostMapping("/do_login")
    public String do_login(Member member, Model model) throws JsonProcessingException {
        // 헤더설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", member.getMemberId());
        map.put("password", member.getPassword());

        String params1 = objectMapper.writeValueAsString(map);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params1, httpHeaders);

        // RestTemplate의 exchange 메서드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://218.145.110.135:8000/auth/members/signin", HttpMethod.POST, entity, String.class);

        // 요청후 응답 확인
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return "index";
    }

}
