package pineapple.pinemvc.module.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pineapple.pinemvc.module.member.dto.Member;
import pineapple.pinemvc.module.member.dto.request.signupMember;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Value("${pineapple.pine-auth-url}")
    private String authServer;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/signin")
    public String signIn() {
        return "page-signin";
    }

    @PostMapping("/signin")
    public String signIn(Member member, Model model, HttpServletResponse response) throws JsonProcessingException {
//        int SubNum = 123;
//        int Pnum = 1;
//        Object Pcode = "코드";
//        SubmitData requestDto = SubmitData.builder()
//                .SubNum(SubNum)
//                .Pnum(Pnum)
//                .Pcode(Pcode)
//                .build();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        HttpEntity<SubmitData> entity = new HttpEntity<>(requestDto, headers);
//
//        String url = "http://localhost:8080/send";
//
//        return restTemplate.exchange(url, HttpMethod.POST, entity, SubmitData.class);
        // 헤더설정
//        HttpHeaders headers = new HttpHeaders();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
        String entityBody = objectMapper.writeValueAsString(member);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(entityBody, httpHeaders);

        // RestTemplate의 exchange 메서드를 통해 URL에 HttpEntity와 함께 요청
        StringBuilder sb = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();
        String signInUrl = "/auth/members/signin";
        sb.append(authServer).append(signInUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);

        //로그인쿠키
        setCookie(response, "memberToken", String.valueOf(responseEntity.getBody()));
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "page-signup";
    }

    @PostMapping("/signup")
    public String signUp(signupMember member, Model model) throws JsonProcessingException {
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
        String signUpUrl = "/auth/members/signup";
        sb.append(authServer).append(signUpUrl);
        ResponseEntity<String> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);
        return "page-signup";
    }

    @PostMapping("/signout")
    public String signOut(HttpServletResponse response) {
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
