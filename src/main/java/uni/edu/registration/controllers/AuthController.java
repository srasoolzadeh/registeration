package uni.edu.registration.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.edu.registration.controllers.dto.LoginDto;
import uni.edu.registration.models.Student;
import uni.edu.registration.services.StudentService;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public Student register(@RequestBody Student student){
        return studentService.registerStudent(student);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDto loginDto){
        return studentService.login(loginDto);
    }
}
