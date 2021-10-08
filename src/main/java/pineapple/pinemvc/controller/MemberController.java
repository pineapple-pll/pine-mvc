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

    @GetMapping("/signup")
    public String signup() {
        return "page-register";
    }

    @PostMapping("/login")
    public String doLogin(Member member, Model model, HttpServletResponse response) throws JsonProcessingException {
        // 헤더설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // Object Mapper를 통한 JSON 바인딩
//        Map<String, Object> map = new HashMap<>();
//        map.put("memberId", member.getMemberId());
//        map.put("password", member.getPassword());
        String entityBody = member.getMemberLoginJson();
        System.out.println(entityBody);
        System.out.println(entityBody);
        System.out.println(entityBody);

//        String params1 = objectMapper.writeValueAsString(map);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(entityBody, httpHeaders);

        // RestTemplate의 exchange 메서드를 통해 URL에 HttpEntity와 함께 요청
        StringBuilder sb = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();
        String loginUrl =  "/auth/members/signin";
        sb.append(authServer).append(loginUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);

        Cookie idCookie = new Cookie("memberToken", String.valueOf(responseEntity.getBody()));
        idCookie.setPath("/");
        idCookie.setMaxAge(60 * 30);//60초 30분
        response.addCookie(idCookie);

        return "redirect:/";
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
}
