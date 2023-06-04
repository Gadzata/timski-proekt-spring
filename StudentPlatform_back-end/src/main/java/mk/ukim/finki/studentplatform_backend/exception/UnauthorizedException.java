package mk.ukim.finki.studentplatform_backend.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(String.format("Unauthorized"));
    }
}

