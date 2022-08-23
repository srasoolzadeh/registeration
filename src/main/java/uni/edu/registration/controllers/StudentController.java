package uni.edu.registration.controllers;

import org.springframework.web.bind.annotation.*;
import uni.edu.registration.models.Student;
import uni.edu.registration.services.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/std")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAll(HttpServletRequest request){
        System.out.println("Get : get all!");
        return studentService.findAll(request);
    }

    @GetMapping("/{stdId}")
    public Student getStd(@PathVariable Long stdId){
        return studentService.getStd(stdId);
    }

    @PutMapping("/{stdId}")
    public Student updateStd(@PathVariable Long stdId, @RequestBody Student student){
        return studentService.updateStudent(stdId, student);
    }

    @DeleteMapping("/{stdId}")
    public boolean deleteStudent(@PathVariable("stdId") Long studentId){
        return studentService.deleteStudent(studentId);
    }
}
