package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer studentEventId;
    @ManyToOne
    Student student;
    @ManyToOne
    Event event;

    public StudentEvent(Student student, Event event) {
        this.student = student;
        this.event = event;
    }
}