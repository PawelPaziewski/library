package pl.paziewski.biblioteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paziewski.biblioteka.entities.Book;
import pl.paziewski.biblioteka.helpers.BookHelper;

import java.security.Principal;
import java.util.List;

@Controller
public class BooksController extends AbstractController {

    private final BookHelper bookHelper;

    @Autowired
    public BooksController(BookHelper bookHelper) {
        this.bookHelper = bookHelper;
    }

    @RequestMapping("/books")
    String show(Principal principal, Model model) {
        clearMessages();
        addLoginToModel(principal, model);
        List<Book> books = bookHelper.getAll();
        model.addAttribute("books", books);
        return "books";
    }
}
