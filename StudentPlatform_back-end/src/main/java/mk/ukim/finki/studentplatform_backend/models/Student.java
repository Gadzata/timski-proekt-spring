package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer studentId;
    String name;
    String surname;
    String email;
    Integer points;

    public Student(String name, String surname, String email, Integer points) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.points = points;
    }
}
