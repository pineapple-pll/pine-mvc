package pineapple.pinemvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pineapple.pinemvc.dto.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class MemberController {

    @Value("${pineapple.authServer}")
    private String authServer;

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
    public String doLogin(Member member, Model model, HttpServletResponse response) throws JsonProcessingException {
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
        String loginUrl =  "/auth/members/signin";
        String loginUrl2 = authServer + loginUrl;

        System.out.println(loginUrl2);
        System.out.println(loginUrl2);
        System.out.println(loginUrl);
        System.out.println(loginUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(loginUrl2, HttpMethod.POST, entity, String.class);
        //ResponseEntity<String> responseEntity = restTemplate.exchange("http://218.145.110.135:8000/auth/members/signin", HttpMethod.POST, entity, String.class);
        //ResponseEntity<String> responseEntity = restTemplate.exchange("http://127.0.0.1:8001/auth/members/signin", HttpMethod.POST, entity, String.class);

        Cookie idCookie = new Cookie("memberToken", String.valueOf(responseEntity.getBody()));
        idCookie.setPath("/");
        idCookie.setMaxAge(60 * 30);//60초 30분
        response.addCookie(idCookie);

        // 요청후 응답 확인
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        return "redirect:/";
    }

    @PostMapping("/do_logout")
    public String doLogout(HttpServletResponse response) {
        expireCookie(response, "memberToken");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
