package pl.paziewski.biblioteka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.entities.User;

@Component
public interface UserDao extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
