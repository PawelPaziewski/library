package pl.paziewski.biblioteka.helpers;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.paziewski.biblioteka.dao.RoleDao;
import pl.paziewski.biblioteka.dao.UserDao;
import pl.paziewski.biblioteka.dto.UserDto;
import pl.paziewski.biblioteka.entities.Role;
import pl.paziewski.biblioteka.entities.User;
import pl.paziewski.biblioteka.mapper.UserMapper;

import java.sql.Date;
import java.util.Optional;

@Getter
@Setter
@Service
public class UserHelper {

    private static final String standardRoleName = "STANDARD_USER";

    private UserDao userDao;
    private PasswordEncoder encoder;
    private RoleDao roleDao;
    private UserMapper userMapper;

    @Autowired
    public UserHelper(UserDao userDao, PasswordEncoder encoder, RoleDao roleDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.roleDao = roleDao;
        this.userMapper = userMapper;
    }

    public boolean isLoginFree(String login) {
        return userDao.findByLogin(login) == null;
    }

    public void addUser(UserDto dto) {
        User user = new User();
        user = userMapper.mapDtoToEntity(dto);
        addStandardRoleToUser(user);
        setInitialValues(user);
        encodeAndSetPassword(dto, user);
        userDao.saveAndFlush(user);
    }

    private void encodeAndSetPassword(UserDto dto, User user) {
        String passwordEncoded = encoder.encode(dto.getPassword());
        dto.setPassword(null);
        user.setPassword(passwordEncoded);
    }

    private void setInitialValues(User user) {
        user.setEnabled(true);
        user.setCreationDate(Date.valueOf(LocalDate.now().toString()));
    }

    private void addStandardRoleToUser(User user) {
        Role standardRole = roleDao.findByRoleName(standardRoleName);
        user.getRoles().add(standardRole);
    }

    User getUser(Optional<String> username) {
        User user;
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        } else {
            user = userDao.findByLogin(username.get());
            if (user == null) {
                throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
            }
        }
        return user;
    }

    public void updateUserDetails(Optional<String> username, UserDto dto) {
        User user = getUser(username);
        userMapper.setUserFieldsFromDto(dto, user);
        userDao.saveAndFlush(user);
    }

    public UserDto getUserDto(Optional<String> username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Wystąpił nieoczekiwany błąd");
        }
        User user = getUser(username);
        return userMapper.mapEntityToDto(user);
    }
}
