package uni.edu.registration.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uni.edu.registration.models.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rasoolzadeh
 */
public class UserMapper {
    public static UserPrincipal userToPrincipal(User user) {
        UserPrincipal userp = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toList());

        userp.setUsername(user.getUsername());
        userp.setPassword(user.getPassword());
        userp.setEnabled(user.isEnabled());
        userp.setAuthorities(authorities);
        return userp;
    }
}
