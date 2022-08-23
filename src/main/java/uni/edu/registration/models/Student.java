package uni.edu.registration.models;



import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by rasoolzadeh
 */
@Entity
@Table(name = "std_tbl")
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private String firstName;
    private String lastName;

    public Student(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
