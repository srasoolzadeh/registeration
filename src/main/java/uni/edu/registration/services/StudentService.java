package uni.edu.registration.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.controllers.dto.LoginDto;
import uni.edu.registration.models.Student;
import uni.edu.registration.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by rasoolzadeh
 */
@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public Student registerStudent(Student student){
        if(student.getEmail()!=null){
            if(!emailFormatMatches(student.getEmail()))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email Format Error!");
            if(studentRepository.findByEmail(student.getEmail()).isPresent())
                throw new ResponseStatusException(HttpStatus.CONFLICT);
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

    public ResponseEntity<String> login(LoginDto loginDto){
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }
}
