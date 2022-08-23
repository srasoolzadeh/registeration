package uni.edu.registration.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import uni.edu.registration.models.UserSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by rasoolzadeh
 */
@Component
public class JwtAgent {
    @Value("${senitor.app.jwtExpirationMs}")
    private Long jwtExpirationInMs;
    @Value("${senitor.app.jwtSecret}")
    private String jwtSecret;



public String createJwt(UserSession userSession){
    Date expiryDate = new Date((new Date()).getTime() + jwtExpirationInMs);
    return Jwts.builder()
            .setSubject(userSession.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
}

public String extractJwtFromRequest(HttpServletRequest request){
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
        return headerAuth.substring(7);
    }
    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,  "TOKEN_IS_UNSUPPORTED");
}

    public String getEmailFromJwtToken(String token) {
        validateJwtToken(token);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("email").toString();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_UNSUPPORTED");
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_UNSUPPORTED");
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_EXPIRED");
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_UNSUPPORTED");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "TOKEN_IS_UNSUPPORTED");
        }
    }
}
