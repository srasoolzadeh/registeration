package uni.edu.registration.controllers.dto;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User update(@RequestBody UserDto userDto, HttpServletRequest request){
        return userService.update(userDto, request);
    }
}
