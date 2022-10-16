package uni.edu.registration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uni.edu.registration.models.Role;
import uni.edu.registration.models.User;
import uni.edu.registration.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        adminUser.setRoles(roles);
        if(userRepository.findAll().isEmpty()){
            userRepository.save(adminUser);
            System.out.println("admin created!");
        }
    }
}
