package mk.ukim.finki.studentplatform_backend.dataInitilizer;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mk.ukim.finki.studentplatform_backend.models.*;
import mk.ukim.finki.studentplatform_backend.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
    private final NoteRepository noteRepository;

    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository,
                           StudentCourseRepository studentCourseRepository, EventRepository eventRepository,
                           StudentEventRepository studentEventRepository, MessageRepository messageRepository,
                           UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, NoteRepository noteRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.eventRepository = eventRepository;
        this.studentEventRepository = studentEventRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.noteRepository = noteRepository;
    }

    @PostConstruct
    public void initData() {

        //Users
        User user = new User("student1@students.finki.ukim.mk", passwordEncoder.encode("password"));
        User user2 = new User("student2@students.finki.ukim.mk", passwordEncoder.encode("password2"));
        User user3 =  new User("student3@students.finki.ukim.mk", passwordEncoder.encode("password3"));
        User user4 = new User("student4@students.finki.ukim.mk", passwordEncoder.encode("password4"));
        User user5 = new User("student5@students.finki.ukim.mk", passwordEncoder.encode("password5"));

        //Students
        Student student1 = new Student(user.getEmail(),user.getPassword(),"Student1", "Student1Surname", 7);
        studentRepository.save(student1);


        Student student2 = new Student(user2.getEmail(),user2.getPassword(),"Student2", "Student2Surname", 5);
        studentRepository.save(student2);

        Student student3 = new Student(user3.getEmail(), user3.getPassword(), "Student3", "Student3Surname", 7);
        studentRepository.save(student3);

        Student student4 = new Student(user4.getEmail(), user4.getPassword(), "Student4", "Student4Surname", 10);
        studentRepository.save(student4);

        Student student5 = new Student(user5.getEmail(), user5.getPassword(), "Student5", "Student5Surname", 15);
        studentRepository.save(student5);

        //Courses
        Course course1 = new Course("Algorithms and data structures","Introduction to basic data " +
                "structures and algorithms needed to understand different technologies (e.g. databases, application " +
                "development frameworks)",5,false);
        courseRepository.save(course1);

        Course course2 = new Course("Design of interaction computer-human","designing interactive systems, " +
                "design phases (collection and analysis of requirements, prototyping, implementation and usability testing)");
        courseRepository.save(course2);

        Course course3 = new Course("Team project","Team project with minimum 3 students, with mentorship " +
                "from one professor",10,false);
        courseRepository.save(course3);

        Course course4 = new Course("Object oriented programming","concepts of classes and objects will " +
                "be introduced, encapsulation, inheritance and polymorphism",50,true);
        courseRepository.save(course4);

        Course course5 = new Course("Programming of video games","basic components of video " +
                "games and video game programming approaches");
        courseRepository.save(course5);

        Course course6 = new Course("Web based systems","Learning and using the technologies of the " +
                "Semantic Web and Linked Data. Intelligent applications based on Linked Data, and " +
                "Open Data datasets.",20,false);
        courseRepository.save(course6);

        Course course7 = new Course("Digital libraries","description",33,true);
        courseRepository.save(course7);

        Course course8 = new Course("Media and communications","new media, and how they " +
                "fit in different sociological environments");
        courseRepository.save(course8);

        Course course9 = new Course("Software quality and testing","The goal of this course is " +
                "to understand the need for software testing, different techniques of software modeling, " +
                "and using those models for testing");
        courseRepository.save(course9);

        Course course10 = new Course("Databases","basic concepts for using the databases; " +
                "the ways of their modeling and implementation; as well as the application of the query languages");
        courseRepository.save(course10);

        Course course11 = new Course("Integrated systems","design, select, " +
                "implement and manage enterprise IT solutions");
        courseRepository.save(course11);

        Course course12 = new Course("Web programmming","Web application development, using the MVC pattern");
        courseRepository.save(course12);

        Course course13 = new Course("Discrete mathematics","To introduce students to basic mathematical " +
                "concepts as a foundation for the following courses in information technologies, computer and software engineering.");
        courseRepository.save(course13);

        Course course14 = new Course("Web based systems","The students will learn how to develop intelligent" +
                " applications based on Linked Data, and discover and use Open Data datasets.");
        courseRepository.save(course14);

        Course course15 = new Course("Marketing","marketing problems through analytical tools (frameworks," +
                " concepts, models and techniques); Examples of case studies how enterprises are organizing their marketing " +
                "operations, with an emphasis on ICT enterprises");
        courseRepository.save(course15);

        //for random studying, every student has this course automatically
        Course course16 = new Course("Random","every student can access this course");
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

        Event event2 = new Event("Event2", new Date(), new Date(), student1, course1, "Location2",5);
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
        StudentEvent studentEvent2 = new StudentEvent(student1, event1);
        studentEventRepository.save(studentEvent2);
        StudentEvent studentEvent3 = new StudentEvent(student3, event1);
        studentEventRepository.save(studentEvent3);
        StudentEvent studentEvent4 = new StudentEvent(student2, event6);
        studentEventRepository.save(studentEvent4);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DAY_OF_MONTH, 30);

        Date manuallySetDate = calendar.getTime();

        //Messages
        Message message1 = messageRepository.save(new Message(studentEvent1, "Hello from Student1", new Date()));
        Message message2 = messageRepository.save(new Message(studentEvent1, "Hi from Student2",manuallySetDate));
        Message message3 = messageRepository.save(new Message(studentEvent2,"Hey from Student2",new Date()));
        Message message4 = messageRepository.save(new Message(studentEvent3, "Good luck from Student3", new Date()));
        Message message5 = messageRepository.save(new Message(studentEvent3, "Wishing you success", new Date()));
        Message message6 = messageRepository.save(new Message(studentEvent3, "You can do it", new Date()));
        Message message7 = messageRepository.save(new Message(studentEvent2, "How are you feeling", new Date()));
        Message message8 = messageRepository.save(new Message(studentEvent2, "Are you ready", new Date()));

        //String name, String type, byte[] data, Integer courseId

        StringBuilder buffer = getMimeBuffer();
        byte[] encodedAsBytes = buffer.toString().getBytes();
        Note note1 = new Note("Note 1", "application/pdf", encodedAsBytes,1);
        Note note2 = new Note("Note 2", "application/pdf", encodedAsBytes,2);
        Note note3 = new Note("Note 3", "application/pdf", encodedAsBytes,2);
        Note note4 = new Note("Note 4", "application/pdf", encodedAsBytes,3);
        Note note5 = new Note("Note 5", "application/pdf", encodedAsBytes,4);
        Note note6 = new Note("Note 6", "application/pdf", encodedAsBytes,1);

        noteRepository.save(note1);
        noteRepository.save(note2);
        noteRepository.save(note3);
        noteRepository.save(note4);
        noteRepository.save(note5);
        noteRepository.save(note6);

    }

    private static StringBuilder getMimeBuffer() {
        StringBuilder buffer = new StringBuilder();
        for (int count = 0; count < 10; ++count) {
            buffer.append(UUID.randomUUID().toString());
        }
        return buffer;
    }
}

