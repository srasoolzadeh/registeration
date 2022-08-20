package uni.edu.registration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.edu.registration.models.Course;

/**
 * Created by rasoolzadeh
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
