package uni.edu.registration.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.config.JwtAgent;
import uni.edu.registration.controllers.dto.LoginDto;
import uni.edu.registration.models.Role;
import uni.edu.registration.models.Student;
import uni.edu.registration.models.UserSession;
import uni.edu.registration.repositories.StudentRepository;
import uni.edu.registration.repositories.UserSessionRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by rasoolzadeh
 */
@Service
public class StudentService {
    private StudentRepository studentRepository;
    private UserSessionRepository userSessionRepository;
    private final JwtAgent jwtAgent;

    public StudentService(StudentRepository studentRepository, UserSessionRepository userSessionRepository, JwtAgent jwtAgent) {
        this.studentRepository = studentRepository;
        this.userSessionRepository = userSessionRepository;
        this.jwtAgent = jwtAgent;
    }

    public List<Student> findAll(HttpServletRequest request){
        System.out.println("--> find all call: "+request.getAuthType());
        Student student = findStudentByRequest(request);
        return studentRepository.findAll();
    }
    private Student findStudentByRequest(HttpServletRequest request){
        String jwt = jwtAgent.extractJwtFromRequest(request);
        String email = jwtAgent.getEmailFromJwtToken(jwt);
        System.out.println("===> email from jwt: "+email);
        return findStudentByEmail(email);
    }

    private Student findStudentByEmail(String email){
        Optional<Student> student = studentRepository.findByEmail(email);
        if(student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student with email: "+email+"not found!");
        return student.get();
    }
    public Student registerStudent(Student student){
        if(student.getEmail()!=null){
            if(!emailFormatMatches(student.getEmail()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email Format Error!");
            if(studentRepository.findByEmail(student.getEmail()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Student with email: "+student.getEmail()+" already registered!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "email is null!");
        }
        student.setPassword( (new BCryptPasswordEncoder()).encode(student.getPassword()));
        return studentRepository.save(student);
    }

    public Student getStd(Long id){
        if(studentRepository.findById(id).isPresent())
            return studentRepository.findById(id).get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "student with id="+id+" not exists!");
    }

    public Student updateStudent(Long id, Student newStudent){
        Optional<Student> foundStudent = studentRepository.findById(id);
        if(foundStudent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if(newStudent.getEmail()!=null){
            if(!emailFormatMatches(newStudent.getEmail()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email Format Error!");
            if(studentRepository.findByEmail(newStudent.getEmail()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            foundStudent.get().setEmail(newStudent.getEmail());
        }
        if(newStudent.getFirstName()!=null) foundStudent.get().setFirstName(newStudent.getFirstName());
        if(newStudent.getLastName()!=null) foundStudent.get().setLastName(newStudent.getLastName());
        if(newStudent.getPassword()!=null) foundStudent.get().setPassword(newStudent.getPassword());
        return studentRepository.save(foundStudent.get());
    }

    public boolean deleteStudent(Long id) {
        Optional<Student> foundStudent = studentRepository.findById(id);
        if (foundStudent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        studentRepository.delete(foundStudent.get());
        return true;
    }

    private boolean emailFormatMatches(String email){
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public String login(LoginDto loginDto, HttpServletRequest request){
        Optional<Student> student = studentRepository.findByEmail(loginDto.getUsername());
        if(student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username(email) not found!");
        if(!(new BCryptPasswordEncoder()).matches(loginDto.getPassword(), student.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "password is incorrect!");
        Optional<UserSession> studentSession = userSessionRepository.findByEmail(student.get().getEmail());
        if(studentSession.isPresent()){
            System.out.println("student session exists");
            studentSession.get().setIp(request.getHeader("x-real-ip"));
            studentSession.get().setUserAgent(request.getHeader("User-Agent"));
            UserSession updatedStudent = userSessionRepository.save(studentSession.get());
            return jwtAgent.createJwt(updatedStudent);
        }
        UserSession userSession = new UserSession();
        userSession.setUserAgent(request.getHeader("User-Agent"));
        userSession.setIp(request.getHeader("x-real-ip"));
        userSession.setEmail(loginDto.getUsername());
        userSession.setPermanent(true);
        userSession.setRole(Role.USER);
        userSessionRepository.save(userSession);
        System.out.println("student session created");
        return jwtAgent.createJwt(userSession);

    }
}
