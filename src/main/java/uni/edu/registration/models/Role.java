package uni.edu.registration.models;

import uni.edu.registration.enums.Roles;

import javax.persistence.*;

/**
 * Created by rasoolzadeh
 */
@Entity
@Table(name="role_tbl")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getRoleName() {
        return roleName;
    }

    public void setRoleName(Roles roleName) {
        this.roleName = roleName;
    }
}
