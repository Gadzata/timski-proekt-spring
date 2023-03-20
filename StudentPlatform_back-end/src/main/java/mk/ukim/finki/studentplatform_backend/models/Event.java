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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eventId;
    String name;
    Date dateCreated;
    Date dateScheduled;
    @ManyToOne
    StudentCourse studentCourse;
    String location;

    public Event(String name, Date dateCreated, Date dateScheduled, StudentCourse studentCourse, String location) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateScheduled = dateScheduled;
        this.studentCourse = studentCourse;
        this.location = location;
    }
}
