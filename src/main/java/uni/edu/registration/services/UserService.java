package uni.edu.registration.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.controllers.dto.LoginRequest;
import uni.edu.registration.models.User;
import uni.edu.registration.models.UserSession;
import uni.edu.registration.repositories.UserRepository;
import uni.edu.registration.repositories.UserSessionRepository;
import uni.edu.registration.security.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by rasoolzadeh
 */
@Service
public class UserService {
    private UserRepository userRepository;
    private UserSessionRepository userSessionRepository;
    private JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, UserSessionRepository userSessionRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.jwtUtils = jwtUtils;
    }

    public User register(User user){
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username registered: "+user.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public String login(LoginRequest loginRequest , HttpServletRequest request){
        Optional<User> foundUser = userRepository.findByUsername(loginRequest.getUsername());
        if(foundUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username Not found: "+loginRequest.getUsername());
        if(!(new BCryptPasswordEncoder().matches(loginRequest.getPassword(), foundUser.get().getPassword())))
        //if(!loginRequest.getPassword().equals(foundUser.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "password is incorrect!");
        Optional<UserSession> foundSession = userSessionRepository.findByUsername(loginRequest.getUsername());
        if(foundSession.isPresent()){
            foundSession.get().setUserAgent(request.getHeader("User-Agent"));
            foundSession.get().setIp(request.getHeader("x-real-ip"));
            foundSession.get().setRoles(foundUser.get().getRoles());
            userSessionRepository.save(foundSession.get());
            return jwtUtils.generateToken(foundSession.get());
        }
        UserSession userSession = userSessionRepository.save(
                UserSession.builder()
                        .username(foundUser.get().getUsername())
                        .UserAgent(request.getHeader("User-Agent"))
                        .ip(request.getHeader("x-real-ip"))
                        .roles(foundUser.get().getRoles())
                        .build());
        return jwtUtils.generateToken(userSession);
    }
}
