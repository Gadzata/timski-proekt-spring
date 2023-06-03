package mk.ukim.finki.studentplatform_backend.dataInitilizer;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
public class DataInitializer {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentCourseRepository studentCourseRepository;
    private EventRepository eventRepository;
    private StudentEventRepository studentEventRepository;
    private MessageRepository messageRepository;

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository,
                           StudentCourseRepository studentCourseRepository, EventRepository eventRepository,
                           StudentEventRepository studentEventRepository, MessageRepository messageRepository,
                           UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.eventRepository = eventRepository;
        this.studentEventRepository = studentEventRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {

        //Users
        User user = new User("user", passwordEncoder.encode("password"));
        User user2 = new User("user2", passwordEncoder.encode("password2"));
        User user3 =  new User("user3", passwordEncoder.encode("password3"));
        User user4 = new User("user4", passwordEncoder.encode("password4"));
        User user5 = new User("user5", passwordEncoder.encode("password5"));

        //Students
        Student student1 = new Student(user.getUsername(),user.getPassword(),"Student1", "Student1Surname", "student1@students.finki.ukim.mk", 0);
        studentRepository.save(student1);


        Student student2 = new Student(user2.getUsername(),user2.getPassword(),"Student2", "Student2Surname", "student2@students.finki.ukim.mk", 5);
        studentRepository.save(student2);

        Student student3 = new Student(user3.getUsername(), user3.getPassword(), "Student3", "Student3Surname", "student3@students.finki.ukim.mk", 7);
        studentRepository.save(student3);

        Student student4 = new Student(user4.getUsername(), user4.getPassword(), "Student4", "Student4Surname", "student4@students.finki.ukim.mk", 10);
        studentRepository.save(student4);

        Student student5 = new Student(user5.getUsername(), user5.getPassword(), "Student5", "Student5Surname", "student5@students.finki.ukim.mk", 15);
        studentRepository.save(student5);

        //Courses
        Course course1 = new Course("Algorithms and data structures",5,false);
        courseRepository.save(course1);

        Course course2 = new Course("Design of interaction computer-human");
        courseRepository.save(course2);

        Course course3 = new Course("Team project",10,false);
        courseRepository.save(course3);

        Course course4 = new Course("Object oriented programming",50,true);
        courseRepository.save(course4);

        Course course5 = new Course("Programming of video games");
        courseRepository.save(course5);

        Course course6 = new Course("Web based systems",20,false);
        courseRepository.save(course6);

        Course course7 = new Course("Digital libraries",33,true);
        courseRepository.save(course7);

        Course course8 = new Course("Media and communications");
        courseRepository.save(course8);

        Course course9 = new Course("Software quality and testing");
        courseRepository.save(course9);

        Course course10 = new Course("Databases");
        courseRepository.save(course10);

        Course course11 = new Course("Integrated systems");
        courseRepository.save(course11);

        Course course12 = new Course("Web programmming");
        courseRepository.save(course12);

        Course course13 = new Course("Discrete mathematics");
        courseRepository.save(course13);

        Course course14 = new Course("Web based systems");
        courseRepository.save(course14);

        Course course15 = new Course("Marketing");
        courseRepository.save(course15);

        //for random studying, every student has this course automatically
        Course course16 = new Course("Random");
        courseRepository.save(course16);

        //StudentCourse
        StudentCourse studentCourse1 = new StudentCourse(student1, course1);
        studentCourseRepository.save(studentCourse1);

        StudentCourse studentCourse2 = new StudentCourse(student2, course2);
        studentCourseRepository.save(studentCourse2);

        StudentCourse studentCourse3 = new StudentCourse(student3, course3);
        studentCourseRepository.save(studentCourse3);

        //Events
        Event event1 = new Event("Event1", new Date(), new Date(),student1, course1, "Location1",4);
        eventRepository.save(event1);

        Event event2 = new Event("Event2", new Date(), new Date(), student5, course1, "Location2",5);
        eventRepository.save(event2);

        Event event3 = new Event("Event3", new Date(), new Date(), student2, course3, "Location3",6);
        eventRepository.save(event3);

        Event event4 = new Event("Event4", new Date(), new Date(), student2, course2, "Location4",7);
        eventRepository.save(event4);

        // add more events with different dates
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + (1000 * 60 * 60 * 24));
        Date nextWeek = new Date(now.getTime() + (1000 * 60 * 60 * 24 * 7));
        Event event5 = new Event("Event5", now, tomorrow,student2, course3, "Location5",4);
        eventRepository.save(event5);

        Event event6 = new Event("Event6", now, nextWeek,student2, course2, "https://meet.google.com/niq-gpay-yxy",3);
        eventRepository.save(event6);

        // Create a couple of StudentEvent objects
        StudentEvent studentEvent1 = new StudentEvent(student1, event1);
        studentEventRepository.save(studentEvent1);
        StudentEvent studentEvent2 = new StudentEvent(student2, event1);
        studentEventRepository.save(studentEvent2);
        StudentEvent studentEvent3 = new StudentEvent(student3, event1);
        studentEventRepository.save(studentEvent3);
        StudentEvent studentEvent4 = new StudentEvent(student2, event6);
        studentEventRepository.save(studentEvent4);

        //Messages
        Message message1 = messageRepository.save(new Message(studentEvent1, "Hello from Student1", new Date()));
        Message message2 = messageRepository.save(new Message(studentEvent1, "Hi from Student2",new Date()));
        Message message3 = messageRepository.save(new Message(studentEvent2,"Hey from Student2",new Date()));
        Message message4 = messageRepository.save(new Message(studentEvent3, "Good luck from Student3", new Date()));
        Message message5 = messageRepository.save(new Message(studentEvent3, "Wishing you success", new Date()));
        Message message6 = messageRepository.save(new Message(studentEvent3, "You can do it", new Date()));
        Message message7 = messageRepository.save(new Message(studentEvent2, "How are you feeling", new Date()));
        Message message8 = messageRepository.save(new Message(studentEvent2, "Are you ready", new Date()));



    }
}

