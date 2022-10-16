package uni.edu.registration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.edu.registration.models.Report;

/**
 * Created by rasoolzadeh
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
