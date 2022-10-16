package uni.edu.registration.controllers.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.edu.registration.models.User;
import uni.edu.registration.models.UserSession;
import uni.edu.registration.services.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterUserDto registerUserDto){
        return userService.register(registerUserDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        return userService.login(loginRequest, request);
    }

    @PostMapping("/login2")
    public String login2(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        return userService.login(loginRequest, request);
    }
}
