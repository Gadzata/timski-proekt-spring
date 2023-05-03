package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.models.StudentCourse;
import mk.ukim.finki.studentplatform_backend.repository.StudentCourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCourseService {
    private final StudentCourseRepository repository;

    public StudentCourseService(StudentCourseRepository repository) {
        this.repository = repository;
    }

    public List<StudentCourse> getAllStudentCourses() {
        return repository.findAll();
    }

    public StudentCourse getStudentCourseById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public StudentCourse saveStudentCourse(Student student, Course course) {
        StudentCourse studentCourse = new StudentCourse(student, course);
        return repository.save(studentCourse);
    }

    public void deleteStudentCourseById(Integer id) {
        repository.deleteById(id);
    }

    public List<Course> getCoursesByStudent(Student student) {
        List<StudentCourse> studentCourses = repository.findByStudent(student);
        List<Course> courses = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            courses.add(studentCourse.getCourse());
        }
        return courses;
    }

    public List<StudentCourse> getStudentCoursesByCourse(Course course) {
        return repository.findByCourse(course);
    }

}
