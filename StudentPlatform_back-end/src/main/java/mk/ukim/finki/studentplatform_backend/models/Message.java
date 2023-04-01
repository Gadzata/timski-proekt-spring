package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer messageId;
    @ManyToOne
    StudentEvent studentEvent;
    String text;
    Date dateWritten;

    public Message(StudentEvent studentEvent, String text, Date dateWritten) {
        this.studentEvent = studentEvent;
        this.text = text;
        this.dateWritten = dateWritten;
    }
}
