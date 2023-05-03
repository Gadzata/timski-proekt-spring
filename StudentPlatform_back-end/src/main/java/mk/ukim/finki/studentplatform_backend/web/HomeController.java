package mk.ukim.finki.studentplatform_backend.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.service.CourseService;
import mk.ukim.finki.studentplatform_backend.service.StudentCourseService;
import mk.ukim.finki.studentplatform_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private StudentCourseService studentCourseService;
    @Autowired
    private StudentService studentService;

//    for when we use spring security
//    @GetMapping
//    public ResponseEntity<List<Course>> showCoursesForStudents(Authentication authentication) {
//        String username = authentication.getName();
    @GetMapping
    public ResponseEntity<List<Course>> showCoursesForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Student student = studentService.getStudentByEmail(username);
        List<Course> courses = studentCourseService.getCoursesByStudent(student);
        return ResponseEntity.ok(courses);
    }

}
