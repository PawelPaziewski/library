package pl.paziewski.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.paziewski.biblioteka.dto.UserDto;
import pl.paziewski.biblioteka.helpers.UserHelper;
import pl.paziewski.biblioteka.validation.UserValidation;

@Controller
public class RegistrationController extends AbstractController {

    private final UserHelper userHelper;
    private final UserValidation userValidation;

    @Autowired
    public RegistrationController(UserHelper userHelper, UserValidation userValidation) {
        this.userHelper = userHelper;
        this.userValidation = userValidation;
    }

    @GetMapping("/register")
    String register(Model model) {
        clearMessages();
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    String addUser(UserDto dto, Model model) {
        clearMessages();
        errors = userValidation.validate(dto);
        if (!errors.isEmpty()) {
            dto.setPassword("");
            model.addAttribute("user", dto);
            model.addAttribute("errors", errors);
            return "registration";
        } else {
            infos.add("Zarejestrowałeś się. Teraz zaloguj się");
            model.addAttribute("infos", infos);
            userHelper.addUser(dto);
            return "loginPage";
        }
    }
}
