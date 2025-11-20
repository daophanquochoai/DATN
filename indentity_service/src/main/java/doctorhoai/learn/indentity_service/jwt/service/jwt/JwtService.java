package doctorhoai.learn.indentity_service.jwt.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(final String token);
    Date extractExpiration(final String token);
    <T> T extractClaim(final String token, final Function<Claims, T> claimsTFunction);
    String generateToken(final UserDetails userDetails);
    String generateRefreshToken(final UserDetails userDetails);
    boolean validateToken(final String token, final UserDetails user);
}
