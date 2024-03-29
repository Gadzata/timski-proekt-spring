package mk.ukim.finki.studentplatform_backend.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.exception.UnauthorizedException;
import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.service.EventService;
import mk.ukim.finki.studentplatform_backend.service.MessageService;
import mk.ukim.finki.studentplatform_backend.service.StudentEventService;
import mk.ukim.finki.studentplatform_backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestApi {

    private final EventService eventService;
    private final MessageService messageService;
    private final StudentEventService studentEventService;
    private final StudentService studentService;

    public EventRestApi(EventService eventService, MessageService messageService, StudentEventService studentEventService,
                        StudentService studentService) {
        this.eventService = eventService;
        this.messageService = messageService;
        this.studentEventService = studentEventService;
        this.studentService = studentService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return events;
    }

    @GetMapping("/upcoming")
    public List<Event> getAllUpcomingEvents() {
        List<Event> upcomingEvents = eventService.getAllUpcomingEvents();
        return upcomingEvents;
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable("eventId") Integer eventId) {
        try {
            Event event = eventService.getEventById(eventId);
            return event;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Event createEvent(@RequestParam String name,
                             @RequestParam Date dateCreated,
                             @RequestParam Date dateScheduled,
                             @RequestParam Integer studentId,
                             @RequestParam Integer courseId,
                             @RequestParam String location,
                             @RequestParam Integer numOfStudents) {
        Event event = eventService.createEvent(name, dateCreated, dateScheduled, studentId, courseId, location, numOfStudents);
        return event;
    }

    @PutMapping("/{eventId}")
    public void updateEvent(@PathVariable("eventId") Integer eventId,
                            @RequestParam String name,
                            @RequestParam Date dateCreated,
                            @RequestParam Date dateScheduled,
                            @RequestParam Integer studentId,
                            @RequestParam Integer courseId,
                            @RequestParam String location,
                            @RequestParam Integer numOfStudents) {
        try {
            eventService.updateEvent(eventId, name, dateCreated, dateScheduled, studentId, courseId, location, numOfStudents);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable("eventId") Integer eventId) {
        try {
            eventService.deleteEvent(eventId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /// Discussion section

    @PostMapping("/{eventId}/create")
    public Message createMessage(@PathVariable Integer eventId, @RequestParam String text, @RequestParam java.sql.Date dateWritten, HttpServletRequest request) {
        Event event = eventService.getEventById(eventId);
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        StudentEvent studentEvent = studentEventService.findStudentEventByEventIdAndStudentId(student.getUserId(),eventId);
        return messageService.createMessage(studentEvent, text, dateWritten);
    }

    @GetMapping("/{eventId}/discussion")
    public List<Message> getAllByStudentEventOrderByDateWritten (@PathVariable("eventId") Integer eventId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Student student = (Student) request.getSession().getAttribute("user");
        if (student == null)
            throw new UnauthorizedException();
        return messageService.getMessagesByEvent(eventService.getEventById(eventId));
    }
}
