package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.Course;
import mk.ukim.finki.studentplatform_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByCourse(Course course);
}