package mk.ukim.finki.studentplatform_backend.service;


import jakarta.transaction.Transactional;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.models.User;
import mk.ukim.finki.studentplatform_backend.repository.StudentRepository;
import mk.ukim.finki.studentplatform_backend.repository.UserRepository;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

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

    public User findByUsername(String email) throws InvalidCredentialsException {
        return userRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
    }

    @Transactional
    public User register(String email, String password, String repeatPassword,String name,String surname,Integer points) throws InvalidCredentialsException {
        if (!password.equals(repeatPassword)) {
            throw new InvalidCredentialsException();
        }
        if (checkIfExists(email).isPresent()) {
            throw new InvalidCredentialsException();
        }
        if(!email.endsWith("@students.finki.ukim.mk"))
            throw new InvalidCredentialsException();
        Student student = new Student(email, passwordEncoder.encode(password), name, surname,points);
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

    public User login(String email, String password) throws InvalidCredentialsException {
        User user = findByUsername(email);
        if (passwordEncoder.matches(password, user.getPassword()))
            return user;
        throw new InvalidCredentialsException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}