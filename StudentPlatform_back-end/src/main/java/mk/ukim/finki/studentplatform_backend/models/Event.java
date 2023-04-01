package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    @OneToOne
    Student createdBy;
    @ManyToOne
    Course course;
    String location;
    Integer numOfStudents;

    public Event(String name, Date dateCreated, Date dateScheduled, Student createdBy, Course course, String location, Integer numOfStudents) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.dateScheduled = dateScheduled;
        this.createdBy = createdBy;
        this.course = course;
        this.location = location;
        this.numOfStudents = numOfStudents;
    }
}
