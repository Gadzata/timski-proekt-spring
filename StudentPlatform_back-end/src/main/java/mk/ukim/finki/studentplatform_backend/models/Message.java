package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer messageId;
    @ManyToOne
    Student student;
    @ManyToOne
    Event event;
    String text;

    public Message(Student student, Event event, String text) {
        this.student = student;
        this.event = event;
        this.text = text;
    }
}
