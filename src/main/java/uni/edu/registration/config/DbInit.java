package uni.edu.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uni.edu.registration.models.User;
import uni.edu.registration.repositories.UserRepository;

import javax.annotation.PostConstruct;

/**
 * Created by rasoolzadeh
 */
@Configuration
public class DbInit {
    private UserRepository userRepository;

    public DbInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void createAdminUser(){
        if(userRepository.findAll().isEmpty()){
            userRepository.save(User.builder()
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .roles("ADMIN")
                    .build());
        }
    }
}
