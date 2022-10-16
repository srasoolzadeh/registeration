package uni.edu.registration.controllers.dto;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uni.edu.registration.models.User;
import uni.edu.registration.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    //@PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public User update(@RequestBody UserDto userDto, HttpServletRequest request){
        return userService.update(userDto, request);
    }

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable("id") Long userId){
        return userService.getUserInfo(userId);
    }

    @GetMapping("/id")
    public User getInfo(@RequestParam(name="userid") Long user_id){
        return userService.getUserInfo(user_id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") Long user_id){
        return userService.deleteUser(user_id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long user_id, @Valid @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(user_id, userUpdateDto);
    }
}
