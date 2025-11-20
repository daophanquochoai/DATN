package doctorhoai.learn.indentity_service.jwt.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtUtils {
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, final Function<Claims, T> claims);
    String generateToken(final UserDetails userDetails, Long time);
    Boolean validateToken(final String token,final UserDetails userDetails);
}
