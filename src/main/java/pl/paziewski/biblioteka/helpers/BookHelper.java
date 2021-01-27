package pl.paziewski.biblioteka.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.dao.BookDao;
import pl.paziewski.biblioteka.entities.Book;

import java.util.List;

@Component
public class BookHelper {

    private final BookDao bookDao;

    @Autowired
    public BookHelper(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAll() {
        return bookDao.findAll();
    }
}
