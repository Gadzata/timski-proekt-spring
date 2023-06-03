package mk.ukim.finki.studentplatform_backend.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.studentplatform_backend.models.Event;
import mk.ukim.finki.studentplatform_backend.models.Message;
import mk.ukim.finki.studentplatform_backend.models.StudentEvent;
import mk.ukim.finki.studentplatform_backend.service.EventService;
import mk.ukim.finki.studentplatform_backend.service.MessageService;
import mk.ukim.finki.studentplatform_backend.service.StudentEventService;
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

    public EventRestApi(EventService eventService, MessageService messageService, StudentEventService studentEventService) {
        this.eventService = eventService;
        this.messageService = messageService;
        this.studentEventService = studentEventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return events;
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
            Event event = eventService.getEventById(eventId);
            eventService.deleteEvent(eventId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /// Discussion section

    @PostMapping("/create")
    public Message createMessage(@RequestParam StudentEvent studentEvent, @RequestParam String text, @RequestParam java.sql.Date dateWritten) {
        return messageService.createMessage(studentEvent, text, dateWritten);
    }

    @GetMapping("/{eventId}/discussion")
    public List<Message> getAllByStudentEventOrderByDateWritten (@PathVariable("eventId") Integer eventId, HttpServletRequest request){
        // TODO: GET STUDENT ID FROM SESSION
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("studentId");
                return messageService.getAllByStudentEventOrderByDateWritten(this.studentEventService.findStudentEventByEventIdAndStudentId(studentId, eventId));
    }
}
