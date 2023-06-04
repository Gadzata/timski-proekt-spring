package mk.ukim.finki.studentplatform_backend.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.exception.UnauthorizedException;
import mk.ukim.finki.studentplatform_backend.message.ResponseFile;
import mk.ukim.finki.studentplatform_backend.message.ResponseMessage;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Event;
import mk.ukim.finki.studentplatform_backend.models.Note;
import mk.ukim.finki.studentplatform_backend.models.User;
import mk.ukim.finki.studentplatform_backend.service.CourseService;
import mk.ukim.finki.studentplatform_backend.service.EventService;
import mk.ukim.finki.studentplatform_backend.service.NoteService;
import mk.ukim.finki.studentplatform_backend.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseRestAPI {

    private final CourseService courseService;
    private final EventService eventService;
    private final NoteService noteService;
    private final StudentService studentService;

    public CourseRestAPI(CourseService courseService, NoteService noteService, EventService eventService,
                         StudentService studentService) {
        this.courseService = courseService;
        this.noteService = noteService;
        this.eventService = eventService;
        this.studentService = studentService;
    }

    //when a student clicks 'add new course' from the home page
    @GetMapping
    private List<Course> findAll() {
        return this.courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        try {
            return courseService.getCourseById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/upcomingEvents")
    public List<Event> getAllUpcomingEventsInCourse(@PathVariable Integer id) {
        return eventService.getAllUpcomingEventsInCourse(id);
    }

    @GetMapping("/{id}/allEvents")
    public List<Event> getAllEventsInCourse(@PathVariable Integer id) {
        return eventService.getAllEventsInCourse(id);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Course> findById(@PathVariable Integer id) {
//        Optional<Course> course = this.courseService.findCourseById(id);
//        return course.map(c -> ResponseEntity.ok().body(c))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @PostMapping
    public Course createCourse(@RequestParam String name,
                               @RequestParam String description,
                               @RequestParam Integer participants,
                               @RequestParam Boolean done) {
        return courseService.createCourse(name, description, participants, done);
    }


    @PutMapping("/{courseId}")
    public void updateCourse(@PathVariable("courseId") Integer courseId,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Integer participants,
                             @RequestParam Boolean done) {
        try {
            courseService.updateCourse(courseId, description, name, participants, done);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable("courseId") Integer courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{courseId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Integer courseId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String message = "";
        try {
            HttpSession session = request.getSession(false);
            User user = (session != null) ? (User) session.getAttribute("user") : null;
            String username = user.getEmail();
            if (username == null) {
                throw new UnauthorizedException();
            }
            Integer studentId = studentService.getStudentByEmail(username).getUserId();
            noteService.store(file, courseId, studentId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable Integer id) {
        List<ResponseFile> files = noteService.getNotesByCourseId(id).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("courses/files/")
                    .path(dbFile.getNoteId().toString())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer fileId) {
        Note note = noteService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + note.getName() + "\"")
                .contentType(MediaType.valueOf(note.getType()))
                .body(note.getData());
    }


}
