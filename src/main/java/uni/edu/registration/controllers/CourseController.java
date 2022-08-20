package uni.edu.registration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uni.edu.registration.models.Course;
import uni.edu.registration.services.CourseService;

import java.util.List;

/**
 * Created by rasoolzadeh
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> getAll(){
        return courseService.getAll();
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @GetMapping("/id")
    public Course getCourse(@RequestParam(name = "id") Long id){
        return courseService.getCourseInfo(id);
    }
}
