package mk.ukim.finki.studentplatform_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Note;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.repository.CourseRepository;
import mk.ukim.finki.studentplatform_backend.repository.NoteRepository;
import mk.ukim.finki.studentplatform_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    private StudentRepository studentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Optional<Note> store(MultipartFile file, Integer courseId, Integer studentId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Note note = new Note(fileName, file.getContentType(), file.getBytes(), courseId);
        Student student = this.studentRepository.findById(studentId).get();
        student.setPoints(student.getPoints()+5);
        return Optional.of(noteRepository.save(note));
    }

    public Note getFile(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("File not found"));
    }

    @Transactional(readOnly = true)
    public Stream<Note> getNotesByCourseId(Integer courseId) {
        return noteRepository.findByCourseId(courseId).stream();
    }
}
