package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    String name;
    String surname;
    Integer points;

    public Student(String username, String password,String name, String surname, Integer points) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.points = 0;
    }
}
