package mk.ukim.finki.studentplatform_backend.service;

import jakarta.persistence.EntityNotFoundException;
import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public Course findCourseByName(String name){ return courseRepository.findByName(name); }


    public Course createCourse(String name, String description, Integer participants, Boolean done) {
        Course course = new Course(name, description, participants, done);
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, String name, String description, Integer participants, Boolean done) {
        Course existingCourse = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        existingCourse.setName(name);
        existingCourse.setDescription(description);
        existingCourse.setParticipants(participants);
        existingCourse.setDone(done);

        return courseRepository.save(existingCourse);
    }


    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

}