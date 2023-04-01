package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEventRepository extends JpaRepository<StudentEvent, Integer> {
}
