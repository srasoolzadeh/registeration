package uni.edu.registration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.edu.registration.models.UserSession;

import java.util.Optional;

/**
 * Created by rasoolzadeh
 */
@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    public Optional<UserSession> findByUsername(String username);
}
