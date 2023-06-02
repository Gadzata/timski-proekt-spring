package mk.ukim.finki.studentplatform_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class StudentCourseException extends RuntimeException{
    public StudentCourseException() {
        super(String.format("Course already added or no space for another course"));
    }
}
