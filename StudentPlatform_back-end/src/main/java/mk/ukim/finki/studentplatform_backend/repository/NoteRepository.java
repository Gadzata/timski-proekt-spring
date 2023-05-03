package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByCourseId(Integer courseId);
}
