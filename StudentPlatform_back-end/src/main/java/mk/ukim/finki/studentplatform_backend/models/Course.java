package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer courseId;
    String name;
    Integer participants;
    Boolean done;

    public Course(String name) {
        this.name = name;
        this.participants = 0;
        this.done = false;
    }
}
