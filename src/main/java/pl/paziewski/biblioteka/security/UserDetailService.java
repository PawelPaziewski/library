package pl.paziewski.biblioteka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.dao.UserDao;
import pl.paziewski.biblioteka.entities.Role;
import pl.paziewski.biblioteka.entities.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailService implements UserDetailsService {

    UserDao userDao;

    @Autowired
    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    prepareGrantedAuthorities(user.getRoles()));
        }
    }

    private Set<GrantedAuthority> prepareGrantedAuthorities(Set<Role> roles) {
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        return authorities;
    }
}
