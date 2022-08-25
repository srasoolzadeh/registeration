package uni.edu.registration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

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
    private String roles;


}
