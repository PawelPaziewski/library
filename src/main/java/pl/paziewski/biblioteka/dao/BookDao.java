package pl.paziewski.biblioteka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.entities.Book;

@Component
public interface BookDao extends JpaRepository<Book, String> {
}
