package mk.ukim.finki.studentplatform_backend.service;


import jakarta.transaction.Transactional;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.models.User;
import mk.ukim.finki.studentplatform_backend.repository.StudentRepository;
import mk.ukim.finki.studentplatform_backend.repository.UserRepository;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, StudentRepository studentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) throws InvalidCredentialsException {
        return userRepository.findByUsername(username).orElseThrow(InvalidCredentialsException::new);
    }

    @Transactional
    public User register(String username, String password, String repeatPassword,String name,String surname,String email,Integer points) throws InvalidCredentialsException {
        if (!password.equals(repeatPassword)) {
            throw new InvalidCredentialsException();
        }
        if (checkIfExists(username).isPresent()) {
            throw new InvalidCredentialsException();
        }
        Student student = new Student(username, passwordEncoder.encode(password), name, surname,email,points);
        userRepository.save(student);
        studentRepository.save(student);
        return student;
    }

    public Optional<User> checkIfExists(String username) {
        try {
            User byUsername = findByUsername(username);
            if (byUsername != null)
                return Optional.of(byUsername);
            else
                return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public User login(String username, String password) throws InvalidCredentialsException {
        User user = findByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword()))
            return user;
        throw new InvalidCredentialsException();
    }
}