package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.exception.StudentCourseException;
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
    private final StudentService studentService;

    public StudentCourseService(StudentCourseRepository repository, StudentService studentService) {
        this.repository = repository;
        this.studentService = studentService;
    }

    public List<StudentCourse> getAllStudentCourses() {
        return repository.findAll();
    }

    public StudentCourse getStudentCourseById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public StudentCourse saveStudentCourse(Student student, Course course) {
        List<Course> courseList = this.getCoursesByStudent(student);

        if(courseList.size() >= 13 && courseList.contains(course)) {
            throw new StudentCourseException();
        }
        StudentCourse studentCourse = new StudentCourse(student, course);
        student.setPoints(student.getPoints()+1);
        studentService.updateStudent(student.getStudentId(), student);
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
