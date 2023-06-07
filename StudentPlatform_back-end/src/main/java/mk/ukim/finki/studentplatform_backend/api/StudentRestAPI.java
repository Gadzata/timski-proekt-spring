package mk.ukim.finki.studentplatform_backend.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.exception.UnauthorizedException;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Event;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.service.CourseService;
import mk.ukim.finki.studentplatform_backend.service.StudentCourseService;
import mk.ukim.finki.studentplatform_backend.service.StudentEventService;
import mk.ukim.finki.studentplatform_backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestAPI {

    private StudentService studentService;
    private StudentCourseService studentCourseService;
    private StudentEventService studentEventService;
    private CourseService courseService;

    public StudentRestAPI(StudentService studentService, StudentCourseService studentCourseService,
                          StudentEventService studentEventService, CourseService courseService) {
        this.studentService = studentService;
        this.studentCourseService = studentCourseService;
        this.studentEventService = studentEventService;
        this.courseService = courseService;
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
    public void deleteStudentById(@PathVariable Integer studentId) {
        try {
            studentService.deleteStudentById(studentId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //    for when we use spring security
//    @GetMapping
//    public ResponseEntity<List<Course>> showCoursesForStudents(Authentication authentication) {
//        String username = authentication.getName();

//    @GetMapping("/myCourses")
//    public List<Course> showCoursesForStudent(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        String username = (session != null) ? (String) session.getAttribute("username") : null;
//        if (username == null) {
//            throw new UnauthorizedException();
//        }
//        Student student = studentService.getStudentByEmail(username);
//        return studentCourseService.getCoursesByStudent(student);
//    }

    @GetMapping("/getActiveUser")
    public Student getActiveUser(HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        return student;
    }

    @GetMapping("/myCourses")
    public List<Course> showCoursesForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        return studentCourseService.getCoursesByStudent(student);

    }
//    @GetMapping("/myCourses")
//    public ResponseEntity<List<Course>> showCoursesForStudent(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        Student student = (Student) request.getSession().getAttribute("user");
//        if(student==null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
////        String username = (session != null) ? (String) session.getAttribute("user") : null;
////        if (username == null) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
////        }
//        //Student student = studentService.getStudentByEmail(username);
//        List<Course> courses = studentCourseService.getCoursesByStudent(student);
//        return ResponseEntity.ok(courses);
//    }
    @GetMapping("/myEvents")
    public List<Event> showEventsForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        return studentEventService.getEventsByStudent(student);
    }

    @GetMapping("/myUpcomingEvents")
    public List<Event> showUpcomingEventsForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        return studentEventService.getUpcomingEventsByStudent(student);
    }

    @GetMapping("/myPastEvents")
    public List<Event> showPastEventsForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();

//        String username = (session != null) ? (String) session.getAttribute("user") : null;
//        if (username == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
            //Student student = studentService.getStudentByEmail(username);

            return studentEventService.getPastEventsByStudent(student);

    }

    //returns progress as percentage
    @GetMapping("/weekly-progress")
    public Double getWeeklyProgress(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();

        return studentEventService.calculateWeeklyProgress(student);
    }

    @GetMapping("/points")
    public Integer getPoints(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();

        return student.getPoints();
    }

    @GetMapping("/scoreboard")
    public List<Student> getScoreboard(){
        return studentService.getScoreboard();
    }
}