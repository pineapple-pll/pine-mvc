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
import pineapple.pinemvc.dto.request.signupMember;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
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

    @PostMapping("/login")
    public String doLogin(Member member, Model model, HttpServletResponse response) throws JsonProcessingException {
        // 헤더설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
        String entityBody = objectMapper.writeValueAsString(member);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(entityBody, httpHeaders);

        // RestTemplate의 exchange 메서드를 통해 URL에 HttpEntity와 함께 요청
        StringBuilder sb = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl =  "/auth/members/signin";
        sb.append(authServer).append(loginUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);

        //로그인쿠키
        setCookie(response, "memberToken", String.valueOf(responseEntity.getBody()));
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup() {
        return "page-register";
    }

    @PostMapping("/signup")
    public String signupPost(signupMember member, Model model) throws JsonProcessingException {
        // 헤더설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
        String entityBody = objectMapper.writeValueAsString(member);
        System.out.println(entityBody);
        System.out.println(entityBody);
        System.out.println(entityBody);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(entityBody, httpHeaders);

        // RestTemplate의 exchange 메서드를 통해 URL에 HttpEntity와 함께 요청
        StringBuilder sb = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl =  "/auth/members/signup";
        sb.append(authServer).append(loginUrl);
        System.out.println(sb);
        System.out.println(sb);
        System.out.println(sb);

        ResponseEntity<String> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);
        return "page-register";
    }

    @PostMapping("/logout")
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

    private void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {

        Cookie cookie = new Cookie("memberToken", cookieValue);
        cookie.setMaxAge(60 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
