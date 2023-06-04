package mk.ukim.finki.studentplatform_backend.repository;

import mk.ukim.finki.studentplatform_backend.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);
    List<Student> findAllByOrderByPointsDesc();
}