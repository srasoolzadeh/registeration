package uni.edu.registration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by rasoolzadeh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "session_tbl")
public class UserSession {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String UserAgent;
    private String ip;
//    @ElementCollection(targetClass = Role.class)
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;
//

}
