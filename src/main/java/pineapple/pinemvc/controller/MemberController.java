package pineapple.pinemvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "page-login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "page-register";
    }

}
