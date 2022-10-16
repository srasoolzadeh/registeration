package uni.edu.registration.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.controllers.dto.LoginRequest;
import uni.edu.registration.controllers.dto.RegisterUserDto;
import uni.edu.registration.controllers.dto.UserDto;
import uni.edu.registration.controllers.dto.UserUpdateDto;
import uni.edu.registration.models.Role;
import uni.edu.registration.models.User;
import uni.edu.registration.models.UserSession;
import uni.edu.registration.repositories.UserRepository;
import uni.edu.registration.repositories.UserSessionRepository;
import uni.edu.registration.security.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public User register(RegisterUserDto registerUserDto){
        Optional<User> foundUser = userRepository.findByUsername(registerUserDto.getUsername());
        if(foundUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username registered: "+registerUserDto.getUsername());
        return userRepository.save(User.builder()
                .username(registerUserDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(registerUserDto.getPassword()))
                .email(registerUserDto.getEmail())
                .phone(registerUserDto.getPhone())
                .enabled(false)
                .roles(Set.of(Role.USER))
                .firstName(registerUserDto.getFirstName())
                .lastName(registerUserDto.getLastName())
        .build());
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public String login(LoginRequest loginRequest , HttpServletRequest request){
        Optional<User> foundUser = userRepository.findByUsername(loginRequest.getUsername());
        if(foundUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username Not found: "+loginRequest.getUsername());
        if(!(new BCryptPasswordEncoder().matches(loginRequest.getPassword(), foundUser.get().getPassword())))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "password is incorrect!");
        Optional<UserSession> foundSession = userSessionRepository.findByUsername(loginRequest.getUsername());

        Set<Role> userRoles = foundUser.get().getRoles();// new HashSet<Role>();
        System.out.println("-->Login: "+foundUser.get().getUsername()+" , roles: "+userRoles);

        if(foundSession.isPresent()){
            foundSession.get().setUserAgent(request.getHeader("User-Agent"));
            foundSession.get().setIp(request.getHeader("x-real-ip"));
            //foundSession.get().setRoles(foundUser.get().getRoles());
            userSessionRepository.save(foundSession.get());
        }else{
            UserSession userSession = userSessionRepository.save(
                    UserSession.builder()
                            .username(foundUser.get().getUsername())
                            .UserAgent(request.getHeader("User-Agent"))
                            .ip(request.getHeader("x-real-ip"))
//                            .roles(userRoles /*foundUser.get().getRoles()*/)
                            .build());
        }
        return jwtUtils.generateToken(foundUser.get());
    }

    public User update(UserDto userDto, HttpServletRequest request){
        String username = jwtUtils.getUsernameFromJwtToken(jwtUtils.parseJwt(request));
        Optional<User> foundUser = userRepository.findByUsername(username);
        if(foundUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found username: "+username);
        System.out.println("==> username found: "+username);
        if(userDto.getPassword()!=null)
            foundUser.get().setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        if(userDto.getRoles()!=null)
            foundUser.get().setRoles(userDto.getRoles());
        return userRepository.save(foundUser.get());
    }

    public User getUserInfo(Long userId){
        Optional<User> foundUser = userRepository.findById(userId);
        if(foundUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found with ID= "+userId);
        return foundUser.get();
    }

    public String deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found by Id= "+id);});
        if(user.getRoles().contains(Role.ADMIN))
            return "Admin Can not be deleted!";
        userRepository.delete(user);
        return "Delete User by Id= "+id+" Completed!";
    }

    public User updateUser(Long id, UserUpdateDto userUpdateDto){
        User user = userRepository.findById(id).orElseThrow(()->{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found by Id= "+id);});
        if(userUpdateDto.getUsername()!=null)
            user.setUsername(userUpdateDto.getUsername());
        if(userUpdateDto.getEmail()!=null)
            user.setEmail(userUpdateDto.getEmail());
        if(userUpdateDto.getPassword()!=null)
            user.setPassword(new BCryptPasswordEncoder().encode(userUpdateDto.getPassword()));
        if(userUpdateDto.getFirstName()!=null)
            user.setFirstName(userUpdateDto.getFirstName());
        if(userUpdateDto.getLastName()!=null)
            user.setLastName(userUpdateDto.getLastName());
        if(userUpdateDto.getPhone()!=null)
            user.setPhone(userUpdateDto.getPhone());
        if(userUpdateDto.isEnabled())
            user.setEnabled(true);
        return userRepository.save(user);
    }
}
