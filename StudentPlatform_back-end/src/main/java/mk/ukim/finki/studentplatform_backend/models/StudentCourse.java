package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer studentCourseId;
    @ManyToOne
    Student student;
    @ManyToOne
    Course course;

    public StudentCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
