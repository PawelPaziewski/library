package pl.paziewski.biblioteka.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController {

    List<String> infos = new LinkedList<>();
    List<String> errors = new LinkedList<>();

    protected void addLoginToModel(Principal principal, Model model) {
        getUsername(principal).ifPresent(p -> model.addAttribute("login", p.toUpperCase()));
    }

    private boolean isPrincipalAUsernamePasswordAuthenticationToken(Principal principal) {
        return principal != null && principal.getClass() == UsernamePasswordAuthenticationToken.class;
    }

    protected Optional<String> getUsername(Principal principal) {
        if (isPrincipalAUsernamePasswordAuthenticationToken(principal)) {
            return Optional.of(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername().toUpperCase());
        } else {
            return Optional.empty();
        }
    }

    protected void clearMessages() {
        infos.clear();
        errors.clear();
    }
}
