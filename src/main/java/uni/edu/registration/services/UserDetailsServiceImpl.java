package uni.edu.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.config.UserMapper;
import uni.edu.registration.models.User;
import uni.edu.registration.repositories.UserRepository;

import java.util.Optional;

/**
 * Created by rasoolzadeh
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent())
            return UserMapper.userToPrincipal(user.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found: "+username);
    }
}
