package mk.ukim.finki.studentplatform_backend.api;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.studentplatform_backend.api.Dtos.RegisterRequest;
import mk.ukim.finki.studentplatform_backend.config.CustomUsernamePasswordAuthenticationProvider;
import mk.ukim.finki.studentplatform_backend.service.UserService;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserRestApi {

    private final UserService userService;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;


    public UserRestApi(UserService userService, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.userService = userService;

        this.authenticationProvider = authenticationProvider;
    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }



    //ova go vraka cel token
    @GetMapping("/getToken")
    public Authentication getToken(){
        return authenticationProvider.getAuthenticatedUser();
    }


    //WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=A86C03C5274290A87D91722E1C33FC67]
    @GetMapping("/getToken2")
    public String getToken2(){
        return authenticationProvider.getAuthenticatedUserToken();
    }


    // ova ne treba ako koristime default login page
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("academicStatuses");
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        System.out.println(request);
        try {
            userService.register(request.getEmail(),request.getPassword(),
                    request.getRepeatedPassword(),request.getName(),request.getSurname(),request.getPoints());
            return ResponseEntity.ok().body("User registered successfully");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().body("User logged out successfully");
    }
}