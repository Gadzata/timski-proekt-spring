package mk.ukim.finki.studentplatform_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteId;
    private String name;
    private String type;
    @Lob
    private byte[] data;
    private Integer courseId;
    private LocalDate dateUploaded;

    public Note(String name, String type, byte[] data, Integer courseId) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.courseId = courseId;
        this.dateUploaded = LocalDate.now();
    }

}
