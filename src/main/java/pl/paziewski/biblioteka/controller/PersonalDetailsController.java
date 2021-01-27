package pl.paziewski.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.paziewski.biblioteka.dto.UserDto;
import pl.paziewski.biblioteka.helpers.UserHelper;
import pl.paziewski.biblioteka.validation.UserValidation;

import java.security.Principal;
import java.util.List;

@Controller
public class PersonalDetailsController extends AbstractController {

    private final UserValidation userValidation;
    private final UserHelper userHelper;

    @Autowired
    public PersonalDetailsController(UserValidation userValidation, UserHelper userHelper) {
        this.userValidation = userValidation;
        this.userHelper = userHelper;
    }


    @GetMapping("/details")
    String personalDetails(Principal principal, Model model) {
        clearMessages();
        addLoginToModel(principal, model);
        try {
            UserDto dto;
            dto = userHelper.getUserDto(getUsername(principal));
            model.addAttribute("user", dto);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
            model.addAttribute("errors", errors);
            return "home";
        }

        return "personalDetails";
    }

    @PostMapping("/details")
    String correctPersonalDetails(Principal principal, Model model, UserDto dto) {
        clearMessages();
        addLoginToModel(principal, model);
        List<String> errors = userValidation.validateDateOfBirth(dto);
        if (errors.isEmpty()) {
            try {
                userHelper.updateUserDetails(getUsername(principal), dto);
                infos.add("Poprawnie zmieniono dane");
                model.addAttribute("infos", infos);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
                model.addAttribute("errors", errors);
                return "home";
            }
        }
        model.addAttribute("user", dto);
        model.addAttribute("errors", errors);
        return "personalDetails";
    }
}
