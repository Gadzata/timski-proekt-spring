package mk.ukim.finki.studentplatform_backend.service;

import jakarta.persistence.EntityNotFoundException;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Event;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.repository.CourseRepository;
import mk.ukim.finki.studentplatform_backend.repository.EventRepository;
import mk.ukim.finki.studentplatform_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    public EventService(EventRepository eventRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.eventRepository = eventRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        return event;
    }

    public Event createEvent(String name, Date dateCreated, Date dateScheduled, Integer studentId, Integer courseId, String location, Integer numOfStudents) {
        Student createdBy = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        Event event = new Event(name, dateCreated, dateScheduled, createdBy, course, location, numOfStudents);
        return eventRepository.save(event);
    }

    public Event updateEvent(Integer id, String name, Date dateCreated, Date dateScheduled, Integer studentId, Integer courseId, String location, Integer numOfStudents) {
        Student createdBy = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
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
