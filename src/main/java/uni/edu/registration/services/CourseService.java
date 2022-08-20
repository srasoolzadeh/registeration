package uni.edu.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.models.Course;
import uni.edu.registration.repositories.CourseRepository;

import java.util.List;

/**
 * Created by rasoolzadeh
 */
@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public Course getCourseInfo(Long courseId){
        if(courseRepository.findById(courseId).isPresent())
            return courseRepository.findById(courseId).get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "course with id="+courseId+" Not found!");
    }
}
