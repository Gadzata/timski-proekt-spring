package mk.ukim.finki.studentplatform_backend.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {
    private String name;
    private String password;
}