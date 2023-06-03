package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Retrieve a student by ID
    public Student getStudentById(Integer studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        return studentOptional.orElse(null);
    }

    // Retrieve a student by email
    public Student getStudentByEmail(String email) {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        return studentOptional.orElse(null);
    }

    // Create a new student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // Update an existing student
    public Student updateStudent(Integer studentId, Student student) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            student.setStudentId(studentId);
            return studentRepository.save(student);
        }
        return null;
    }

    // Delete a student by ID
    public boolean deleteStudentById(Integer studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            studentRepository.deleteById(studentId);
            return true;
        }
        return false;
    }

}
