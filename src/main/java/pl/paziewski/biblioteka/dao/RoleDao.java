package pl.paziewski.biblioteka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.entities.Role;

@Component
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
