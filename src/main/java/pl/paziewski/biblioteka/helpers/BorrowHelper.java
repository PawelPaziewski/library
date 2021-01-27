package pl.paziewski.biblioteka.helpers;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.dao.BookDao;
import pl.paziewski.biblioteka.dao.BorrowDao;
import pl.paziewski.biblioteka.entities.Book;
import pl.paziewski.biblioteka.entities.Borrow;
import pl.paziewski.biblioteka.entities.BorrowStatus;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BorrowHelper {

    private final BookDao bookDao;
    private final UserHelper userHelper;
    private final BorrowDao borrowDao;

    @Autowired
    public BorrowHelper(BookDao bookDao, UserHelper userHelper, BorrowDao borrowDao) {
        this.bookDao = bookDao;
        this.userHelper = userHelper;
        this.borrowDao = borrowDao;
    }


    public void borrowBook(String isbn, Optional<String> username) {
        Optional<Book> book = bookDao.findById(isbn);
        if (book.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        }
        if (bookNotBorrowedYet(book.get(), username.get())) {
            Borrow borrow = new Borrow();
            borrow.setBook(book.get());
            borrow.setBorrower(userHelper.getUser(username));
            borrow.setBorrowDate(Date.valueOf(LocalDate.now().toString()));
            borrow.setStatus(BorrowStatus.ACTUAL);
            borrowDao.saveAndFlush(borrow);
        } else {
            throw new IllegalArgumentException("Nie możesz wypożyczyć jednocześnie 2 razy tej samej ksiązki");
        }
    }

    private boolean bookNotBorrowedYet(Book book, String userName) {
        List<Borrow> borrow = borrowDao.findByBorrowerLoginAndBookIsbn(userName, book.getIsbn()).stream()
                .filter(p -> p.getStatus().equals(BorrowStatus.ACTUAL)).collect(Collectors.toList());
        return borrow.isEmpty();
    }

    public void returnBook(Long loanId) {
        Optional<Borrow> found = borrowDao.findById(loanId);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        }
        Borrow borrow = found.get();
        borrow.setStatus(BorrowStatus.RETURNED);
        borrow.setReturnDate(Date.valueOf(LocalDate.now().toString()));
        borrowDao.saveAndFlush(borrow);
    }

    public List<Borrow> getActualBorrows(Optional<String> username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        } else {
            return borrowDao.findByBorrowerLoginAndStatus(username.get(), BorrowStatus.ACTUAL)
                    .stream().sorted(Comparator.comparing(Borrow::getBorrowDate).reversed())
                    .collect(Collectors.toList());
        }
    }

    public List<Borrow> getArchivalBorrows(Optional<String> username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        } else {
            return borrowDao.findByBorrowerLoginAndStatus(username.get(), BorrowStatus.RETURNED)
                    .stream().sorted(Comparator.comparing(Borrow::getReturnDate).reversed())
                    .collect(Collectors.toList());
        }
    }
}
