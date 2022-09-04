package uni.edu.registration.controllers.dto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uni.edu.registration.models.User;
import uni.edu.registration.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User update(@RequestBody UserDto userDto, HttpServletRequest request){
        return userService.update(userDto, request);
    }
}
