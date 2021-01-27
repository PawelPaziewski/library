package pl.paziewski.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.paziewski.biblioteka.helpers.BorrowHelper;

import java.security.Principal;

@Controller
public class BorrowController extends AbstractController {

    private final BorrowHelper borrowHelper;

    @Autowired
    public BorrowController(BorrowHelper borrowHelper) {
        this.borrowHelper = borrowHelper;
    }

    @RequestMapping("/borrow")
    String loan(Model model, Principal principal, @RequestParam String button) {
        clearMessages();
        try {
            borrowHelper.borrowBook(button, getUsername(principal));
            infos.add("Książka została wypożyczona");
            model.addAttribute("infos", infos);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
            model.addAttribute("errors", errors);
        }
        return "forward:books";
    }

    @RequestMapping("/return")
    String returnBook(Principal principal, Model model, @RequestParam Long button) {
        clearMessages();
        try {
            borrowHelper.returnBook(button);
            infos.add("Zwróciłeś ksiązkę");
            model.addAttribute("infos", infos);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
            return "forward:books";
        }
        return "forward:myBooks";
    }
}
