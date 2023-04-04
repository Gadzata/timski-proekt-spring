package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.repository.StudentEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentEventService {

    private StudentEventRepository studentEventRepository;

    public List<StudentEvent> getAllStudentEvents() {
        return studentEventRepository.findAll();
    }

    public StudentEvent getStudentEventById(Integer id) {
        Optional<StudentEvent> studentEvent = studentEventRepository.findById(id);
        return studentEvent.orElse(null);
    }

    public StudentEvent createStudentEvent(Student student, Event event) {
        StudentEvent studentEvent = new StudentEvent(student, event);
        return studentEventRepository.save(studentEvent);
    }

    public void deleteStudentEvent(Integer id) {
        studentEventRepository.deleteById(id);
    }

    public StudentEvent updateStudentEvent(StudentEvent updatedStudentEvent) {
        StudentEvent existingStudentEvent = getStudentEventById(updatedStudentEvent.getStudentEventId());
        if (existingStudentEvent == null) {
            return null;
        }
        existingStudentEvent.setStudent(updatedStudentEvent.getStudent());
        existingStudentEvent.setEvent(updatedStudentEvent.getEvent());
        return studentEventRepository.save(existingStudentEvent);
    }

    public List<StudentEvent> getStudentEventByEvent(Event event) {
        return studentEventRepository.findStudentEventByEvent(event);
    }

    public List<StudentEvent> getStudentEventByStudent(Student student) {
        return studentEventRepository.findStudentEventByStudent(student);
    }

}
