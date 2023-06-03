package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.Message;
import mk.ukim.finki.studentplatform_backend.models.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    public List<Message> getAllByStudentEventOrderByDateWritten(StudentEvent studentEvent);
}
