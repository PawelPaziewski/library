package pl.paziewski.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paziewski.biblioteka.entities.Borrow;
import pl.paziewski.biblioteka.helpers.BorrowHelper;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MyBooksController extends AbstractController {

    private final BorrowHelper borrowHelper;

    @Autowired
    public MyBooksController(BorrowHelper borrowHelper) {
        this.borrowHelper = borrowHelper;
    }

    @RequestMapping("/myBooks")
    String myBooks(Model model, Principal principal) {
        addLoginToModel(principal, model);
        Optional<String> username = getUsername(principal);
        try {
            List<Borrow> actualBorrows = borrowHelper.getActualBorrows(username);
            List<Borrow> archivalBorrows = borrowHelper.getArchivalBorrows(username);
            model.addAttribute("borrows", actualBorrows);
            model.addAttribute("borrowsArchival", archivalBorrows);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
            model.addAttribute("errors", errors);
        }
        return "myBooks";
    }
}
