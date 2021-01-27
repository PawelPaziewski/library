package pl.paziewski.biblioteka.mapper;

import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.dto.UserDto;
import pl.paziewski.biblioteka.entities.User;

import java.sql.Date;

@Component
public class UserMapper {

    public void setUserFieldsFromDto(UserDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(Date.valueOf(dto.getDateOfBirth()));
    }

    public User mapDtoToEntity(UserDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(Date.valueOf(dto.getDateOfBirth()));
        return user;
    }

    public UserDto mapEntityToDto(User user) {
        UserDto dto = new UserDto();
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth().toString());
        return dto;
    }
}
