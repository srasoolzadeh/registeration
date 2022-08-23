package uni.edu.registration.models;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Created by rasoolzadeh
 */
@Entity
@Table(name = "user_session_tbl")
public class UserSession {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String userAgent;
    private Boolean isPermanent;
    private String ip;
    private Boolean logout;
    private Instant logoutDate;

    private Role role;

    public UserSession() {
    }

    public UserSession(String email, String userAgent, Boolean isPermanent, String ip, Boolean logout, Instant logoutDate, Role role) {
        this.email = email;
        this.userAgent = userAgent;
        this.isPermanent = isPermanent;
        this.ip = ip;
        this.logout = logout;
        this.logoutDate = logoutDate;
        this.role = role;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Boolean getPermanent() {
        return isPermanent;
    }

    public void setPermanent(Boolean permanent) {
        isPermanent = permanent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getLogout() {
        return logout;
    }

    public void setLogout(Boolean logout) {
        this.logout = logout;
    }

    public Instant getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Instant logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
