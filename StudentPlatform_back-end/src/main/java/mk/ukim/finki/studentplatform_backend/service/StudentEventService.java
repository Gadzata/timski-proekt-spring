package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.repository.StudentEventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentEventService {

    private final StudentEventRepository studentEventRepository;
    private final StudentService studentService;

    public StudentEventService(StudentEventRepository studentEventRepository, StudentService studentService) {
        this.studentEventRepository = studentEventRepository;
        this.studentService = studentService;
    }

    public List<StudentEvent> getAllStudentEvents() {
        return studentEventRepository.findAll();
    }

    public StudentEvent getStudentEventById(Integer id) {
        Optional<StudentEvent> studentEvent = studentEventRepository.findById(id);
        return studentEvent.orElse(null);
    }

    public StudentEvent createStudentEvent(Student student, Event event) {
        StudentEvent studentEvent = new StudentEvent(student, event);
        event.setNumOfStudents(event.getNumOfStudents()+1);
        student.setPoints(student.getPoints()+2);
        studentService.updateStudent(student.getUserId(), student);
        return studentEventRepository.save(studentEvent);
    }

    public void deleteStudentEvent(Integer id) {
        StudentEvent studentEvent = studentEventRepository.findById(id).get();
        Event event = studentEvent.getEvent();
        event.setNumOfStudents(event.getNumOfStudents()-1);
        studentEventRepository.deleteById(id);
    }

    public StudentEvent updateStudentEvent(StudentEvent updatedStudentEvent) {
        StudentEvent existingStudentEvent = getStudentEventById(updatedStudentEvent.getStudentEventId());
        if (existingStudentEvent == null) {
            return null;
        }
        existingStudentEvent.setStudent(updatedStudentEvent.getStudent());
        existingStudentEvent.setEvent(updatedStudentEvent.getEvent());
        return studentEventRepository.save(existingStudentEvent);
    }

    public List<StudentEvent> getStudentEventByEvent(Event event) {
        return studentEventRepository.findStudentEventByEvent(event);
    }

    public List<StudentEvent> getStudentEventByStudent(Student student) {
        return studentEventRepository.findStudentEventByStudent(student);
    }

    public List<Event> getEventsByStudent(Student student) {
        List<StudentEvent> studentEvents = studentEventRepository.findStudentEventByStudent(student);
        List<Event> events = new ArrayList<>();
        for (StudentEvent studentEvent : studentEvents) {
            events.add(studentEvent.getEvent());
        }
        return events;
    }

    public List<Event> getUpcomingEventsByStudent(Student student) {
        Date currentDate = new Date();
        List<StudentEvent> studentEvents = studentEventRepository.findStudentEventByStudent(student);
        List<Event> upcomingEvents = new ArrayList<>();
        for (StudentEvent studentEvent : studentEvents) {
            Event event = studentEvent.getEvent();
            if(event.getDateScheduled().after(currentDate))
                upcomingEvents.add(studentEvent.getEvent());
        }
        return upcomingEvents;
    }

    public List<Event> getPastEventsByStudent(Student student) {
        Date currentDate = new Date();
        List<StudentEvent> studentEvents = studentEventRepository.findStudentEventByStudent(student);
        List<Event> pastEvents = new ArrayList<>();
        for (StudentEvent studentEvent : studentEvents) {
            Event event = studentEvent.getEvent();
            if(event.getDateScheduled().before(currentDate))
                pastEvents.add(studentEvent.getEvent());
        }
        return pastEvents;
    }

    public StudentEvent findStudentEventByEventIdAndStudentId(Integer studentId, Integer eventId) {
        return studentEventRepository.findStudentEventByEvent_EventIdAndAndStudent_UserId(studentId, eventId);
    }

    public double calculateWeeklyProgress(Student student) {
        Date today = new Date();
        Date oneWeekAgo = new Date(today.getTime() - (1000 * 60 * 60 * 24 * 7));

        List<StudentEvent> attendedEvents = studentEventRepository.findByStudentAndEvent_DateScheduledBetween(student, oneWeekAgo, today);

        int attendedEventCount = attendedEvents.size();
        int requiredEventCount = 4;

        double progress = (double) attendedEventCount / requiredEventCount * 100;

        return progress > 100 ? 100 : progress;
    }

}
