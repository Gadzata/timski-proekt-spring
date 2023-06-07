package mk.ukim.finki.studentplatform_backend.api;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.studentplatform_backend.models.Student;
import mk.ukim.finki.studentplatform_backend.models.User;
import mk.ukim.finki.studentplatform_backend.service.UserService;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRestApi {

    private final UserService userService;


    public UserRestApi(UserService userService) {
        this.userService = userService;

    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }



// ova ne treba ako koristime default login page
    @PostMapping("/login")
    public ResponseEntity<String> login(
                                        HttpServletRequest request,
                                        Model model) {
        try {
            User user = userService.login(request.getParameter("name"),request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            System.out.println(request.getSession().getAttribute("user"));
            Student student = (Student) request.getSession().getAttribute("user");
            User u  = userService.findByUsername(student.getEmail());
            return ResponseEntity.ok().body("User logged in successfully" + request.getSession().getAttribute("user") + u.getEmail());
        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("academicStatuses");
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String repeatedPassword,
                                           @RequestParam String name,
                                           @RequestParam String surname,
                                           @RequestParam Integer points,
                                           Model model) {
        try {
            userService.register(email,password, repeatedPassword,name,surname,points);
            return ResponseEntity.ok().body("User registered successfully");
        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().body("User logged out successfully");
    }
}