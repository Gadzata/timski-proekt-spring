package mk.ukim.finki.studentplatform_backend.dataInitilizer;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.repository.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
public class DataInitializer {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentCourseRepository studentCourseRepository;
    private EventRepository eventRepository;
    private MessageRepository messageRepository;


    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepository,
                           EventRepository eventRepository, MessageRepository messageRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.eventRepository = eventRepository;
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void initData() {

        //Students
        Student student1 = new Student("Student1", "Student1Surname", "student1@students.finki.ukim.mk");
        studentRepository.save(student1);

        Student student2 = new Student("Student2", "Student2Surname", "student2@students.finki.ukim.mk");
        studentRepository.save(student2);

        Student student3 = new Student("Student3", "Student3Surname", "student3@students.finki.ukim.mk");
        studentRepository.save(student3);

        Student student4 = new Student("Student4", "Student4Surname", "student4@students.finki.ukim.mk");
        studentRepository.save(student4);

        Student student5 = new Student("Student5", "Student5Surname", "student5@students.finki.ukim.mk");
        studentRepository.save(student5);

        //Courses
        Course course1 = new Course("Course1");
        courseRepository.save(course1);

        Course course2 = new Course("Course2");
        courseRepository.save(course2);

        Course course3 = new Course("Course3");
        courseRepository.save(course3);

        //StudentCourse
        StudentCourse studentCourse1 = new StudentCourse(student1, course1);
        studentCourseRepository.save(studentCourse1);

        StudentCourse studentCourse2 = new StudentCourse(student2, course2);
        studentCourseRepository.save(studentCourse2);

        StudentCourse studentCourse3 = new StudentCourse(student3, course3);
        studentCourseRepository.save(studentCourse3);

        //Events
        Event event1 = new Event("Event1", new Date(), new Date(),student1, studentCourse1, "Location1",4);
        eventRepository.save(event1);

        Event event2 = new Event("Event2", new Date(), new Date(), student5,studentCourse2, "Location2",5);
        eventRepository.save(event2);

        Event event3 = new Event("Event3", new Date(), new Date(), student2,studentCourse1, "Location3",6);
        eventRepository.save(event3);

        Event event4 = new Event("Event4", new Date(), new Date(), student2,studentCourse2, "Location4",7);
        eventRepository.save(event4);

        // add more events with different dates
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + (1000 * 60 * 60 * 24));
        Date nextWeek = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7));
        Event event5 = new Event("Event5", now, tomorrow,student2, studentCourse1, "Location5",4);
        eventRepository.save(event5);

        Event event6 = new Event("Event6", now, nextWeek,student2, studentCourse2, "Location6",3);
        eventRepository.save(event6);

        //Messages
        Message message1 = messageRepository.save(new Message(student1, event1, "Hello from Student1"));
        Message message2 = messageRepository.save(new Message(student2, event1, "Hi from Student2"));
        Message message3 = messageRepository.save(new Message(student2, event2, "Hey from Student2"));

    }
}

