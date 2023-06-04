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
    String description;
    Integer participants;
    Boolean done;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.participants = 0;
        this.done = false;
    }

    public Course(String name, String description, Integer participants, Boolean done) {
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.done = done;
    }
}
