package pl.paziewski.biblioteka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/loginPage")
    String login() {
        return "loginPage";
    }
}
