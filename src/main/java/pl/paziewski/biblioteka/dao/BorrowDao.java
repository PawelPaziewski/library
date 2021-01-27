package pl.paziewski.biblioteka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.entities.Borrow;
import pl.paziewski.biblioteka.entities.BorrowStatus;

import java.util.List;

@Component
public interface BorrowDao extends JpaRepository<Borrow, Long> {
    List<Borrow> findBorrowsByBorrowerLogin(String username);

    List<Borrow> findByBorrowerLoginAndBookIsbn(String username, String isbn);

    List<Borrow> findByBorrowerLoginAndStatus(String username, BorrowStatus status);
}
