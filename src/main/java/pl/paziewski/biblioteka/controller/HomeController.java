package pl.paziewski.biblioteka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController extends AbstractController {

    @RequestMapping("/home")
    String home(Principal principal, Model model) {
        clearMessages();
        addLoginToModel(principal, model);
        return "home";
    }
}
