package pl.paziewski.biblioteka.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
}
