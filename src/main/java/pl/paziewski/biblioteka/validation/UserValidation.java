package pl.paziewski.biblioteka.validation;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.paziewski.biblioteka.dto.UserDto;
import pl.paziewski.biblioteka.helpers.UserHelper;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserValidation {

    @Autowired
    UserHelper userHelper;

    public List<String> validate(UserDto dto) {
        List<String> errors = validateDateOfBirth(dto);
        if (!userHelper.isLoginFree(dto.getLogin())) {
            errors.add("Podany login jest już zajęty");
            return errors;
        }
        if (dto.getLogin().equals("")) {
            errors.add("Login nie może być pusty");
        }
        if (dto.getPassword().equals("")) {
            errors.add("Hasło nie może być puste");
        }
        return errors;
    }

    public List<String> validateDateOfBirth(UserDto dto) {
        List<String> errors = new LinkedList<>();
        if (dto.getDateOfBirth().equals("")) {
            errors.add("Data urodzenia nie może być pusta");
        } else if (LocalDate.parse(dto.getDateOfBirth()).isAfter(LocalDate.now())) {
            errors.add("Nie możesz założyć konta jeśli się jeszcze nie urodziłeś");
        } else if (LocalDate.parse(dto.getDateOfBirth()).isBefore(LocalDate.now().minusYears(200))) {
            errors.add("Nie uwierzę Ci że żyjesz dłużej jak 200 lat!!");
        }
        return errors;
    }
}
