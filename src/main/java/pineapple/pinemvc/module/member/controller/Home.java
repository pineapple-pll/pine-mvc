package pineapple.pinemvc.module.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
//    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public String homeLogin(
            @CookieValue(value = "memberToken", required = false) String memberToken,
            Model model) {
        if (memberToken == null) {
            return "page-signin";
        }
        //TODO: 회원인지 확인

        return "index";
    }

}
