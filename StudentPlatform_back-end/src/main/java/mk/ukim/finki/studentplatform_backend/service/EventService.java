package mk.ukim.finki.studentplatform_backend.service;

import jakarta.persistence.EntityNotFoundException;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Event;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.repository.CourseRepository;
import mk.ukim.finki.studentplatform_backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final StudentEventService studentEventService;

    public EventService(EventRepository eventRepository, CourseRepository courseRepository, StudentService studentService,
                        StudentEventService studentEventService) {
        this.eventRepository = eventRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.studentEventService = studentEventService;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllUpcomingEvents() {
        Date currentDate = new Date();
        List<Event> events = this.getAllEvents();
        List<Event> upcomingEvents = new ArrayList<>();
        for (Event event : events) {
            if(event.getDateScheduled().after(currentDate))
                upcomingEvents.add(event);
        }
        return upcomingEvents;
    }

    public List<Event> getAllUpcomingEventsInCourse(Integer courseId) {
        Date currentDate = new Date();
        Course course = courseRepository.findById(courseId).get();
        List<Event> events = eventRepository.findByCourse(course);
        List<Event> upcomingEvents = new ArrayList<>();
        for (Event event : events) {
            if(event.getDateScheduled().after(currentDate))
                upcomingEvents.add(event);
        }
        return upcomingEvents;
    }

    public List<Event> getAllEventsInCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId).get();
        return eventRepository.findByCourse(course);
    }

    public Event getEventById(Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    public Event createEvent(String name, Date dateCreated, Date dateScheduled, Integer studentId, Integer courseId, String location, Integer numOfStudents) {
        Student createdBy = studentService.getStudentById(studentId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        Event event = new Event(name, dateCreated, dateScheduled, createdBy, course, location, numOfStudents);
        createdBy.setPoints(createdBy.getPoints()+5);
        studentService.updateStudent(createdBy.getUserId(), createdBy);
        studentEventService.createStudentEvent(createdBy, event);
        return eventRepository.save(event);
    }

    public Event updateEvent(Integer id, String name, Date dateCreated, Date dateScheduled, Integer studentId, Integer courseId, String location, Integer numOfStudents) {
        Student createdBy = studentService.getStudentById(studentId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        event.setName(name);
        event.setDateCreated(dateCreated);
        event.setDateScheduled(dateScheduled);
        event.setCreatedBy(createdBy);
        event.setCourse(course);
        event.setLocation(location);
        event.setNumOfStudents(numOfStudents);
        return eventRepository.save(event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

}
