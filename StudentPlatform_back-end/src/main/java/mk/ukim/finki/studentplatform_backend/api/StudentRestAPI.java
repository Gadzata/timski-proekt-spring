package mk.ukim.finki.studentplatform_backend.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.service.StudentCourseService;
import mk.ukim.finki.studentplatform_backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestAPI {

    private StudentService studentService;
    private StudentCourseService studentCourseService;
    private StudentEventService studentEventService;
    private CourseService courseService;

    public StudentRestAPI(StudentService studentService, StudentCourseService studentCourseService) {
        this.studentService = studentService;
        this.studentCourseService = studentCourseService;
    }

    // Retrieve all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Retrieve a student by ID
    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    // Retrieve a student by email
    @GetMapping("/email/{email}")
    public Student getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email);
    }

    // Create a new student
    //for every new student, the random course is added to their course list
    @PostMapping("/")
    public Student createStudent(@RequestBody Student student) {
        Course random = courseService.findCourseByName("Random");
        studentCourseService.saveStudentCourse(student,random);
        return studentService.createStudent(student);
    }

    // Update an existing student
    @PutMapping("/{studentId}")
    public Student updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        return studentService.updateStudent(studentId, student);
    }

    // Delete a student by ID
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Integer studentId) {
        boolean isDeleted = studentService.deleteStudentById(studentId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //    for when we use spring security
//    @GetMapping
//    public ResponseEntity<List<Course>> showCoursesForStudents(Authentication authentication) {
//        String username = authentication.getName();

    @GetMapping("/myCourses")
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

    //returns progress as percentage
    @GetMapping("/{studentId}/weekly-progress")
    public ResponseEntity<Double> getWeeklyProgress(HttpServletRequest request) {
        // Fetch the student object based on the provided studentId

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        Student student = studentService.getStudentByEmail(username);

        double weeklyProgress = studentEventService.calculateWeeklyProgress(student);

        return ResponseEntity.ok(weeklyProgress);
    }
}