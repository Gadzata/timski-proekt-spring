package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentEventRepository extends JpaRepository<StudentEvent, Integer> {
    List<StudentEvent> findStudentEventByStudent(Student student);
    List<StudentEvent> findStudentEventByEvent(Event event);
    List<StudentEvent> findByStudentAndEvent_DateScheduledBetween(Student student, Date oneWeekAgo, Date now);
    StudentEvent findStudentEventByEvent_EventIdAndAndStudent_UserId(Integer event_eventId, Integer student_studentId);
}
