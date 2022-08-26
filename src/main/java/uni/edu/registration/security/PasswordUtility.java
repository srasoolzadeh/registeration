package uni.edu.registration.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by rasoolzadeh
 */
@Service
public class PasswordUtility {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String bcryptEncryptor(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
