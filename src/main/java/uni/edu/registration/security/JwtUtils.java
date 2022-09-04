package uni.edu.registration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.models.User;
import uni.edu.registration.models.UserSession;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rasoolzadeh
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${senitor.app.jwtSecret}")
    private String jwtSecret;
    @Value("${senitor.app.permanent.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${senitor.app.temp.jwtExpirationMs}")
    private int jwtExpirationMsTemp;
    @Value("${senitor.app.device.jwtExpirationMs}")
    private int deviceJwtExpirationMs;

    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(createClaims(user))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateTokenForEmailConfirmation(String email) {
        return Jwts.builder()
                .setClaims(createClaimsForEmailConfirmation(email))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateTokenRefreshToken(UserSession userSession) {
        return Jwts.builder()
                .setClaims(createClaimsForRefreshToken(userSession))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        validateJwtToken(token);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
    }

    public Collection<? extends GrantedAuthority> getRoleFromJwtToken(String token) {
        validateJwtToken(token);
        String roles = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("roles").toString();
        if (roles.contains(",")){
            return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }else
            return List.of(new SimpleGrantedAuthority(roles));
    }
    public String getEmailFromJwtToken(String token) {
        validateJwtToken(token);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("email").toString();
    }
    public String getDeviceIdentifierFromJwtToken(String token) {
        validateJwtToken(token);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.warn("JWT Exception: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }


    private Claims createClaims(User user) {
        Claims token = new DefaultClaims()
                .setId(user.getUsername())
                .setSubject("access-token")
                .setIssuer("senitor.ir")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs));
        //Optional<String> roles = userSession.getRoles().stream().map(Enum::toString).reduce((r1, r2) -> r1 + "," + r2);
        String roles = user.getRoles();
        token.put("roles", roles);
        return token;
    }
    private Claims createClaimsForEmailConfirmation(String email) {
        Claims token = new DefaultClaims()
                .setSubject("email-confirmation")
                .setIssuer("senitor.ir")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +  jwtExpirationMsTemp));
        token.put("email", email);
        return token;
    }
    private Claims createClaimsForRefreshToken(UserSession userSession) {
        return new DefaultClaims()
                .setId(userSession.getId().toString())
                .setSubject("refresh-token")
                .setIssuer("senitor.ir")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs ));
    }
//    private Claims createDeviceClaims(UserDevice userDevice) {
//        Claims token = new DefaultClaims()
//                .setId(userDevice.getDeviceIdentifier())
//                .setSubject("access-token")
//                .setIssuer("senitor.ir")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + deviceJwtExpirationMs));
//        token.put("roles", "DEVICE");
//        return token;
//    }
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_UNSUPPORTED");
        //throw new NotAcceptableException(GlobalConstants.TOKEN_IS_UNSUPPORTED);
    }
}
