package mk.ukim.finki.studentplatform_backend.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String repeatedPassword;
    private String name;
    private String surname;
    private Integer points;
}