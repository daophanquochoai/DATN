package doctorhoai.learn.indentity_service.jwt.service.jwt;

import doctorhoai.learn.indentity_service.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value(value = "${jwt.token.access}")
    private long ACCESS_TOKEN_TTL;
    @Value(value = "${jwt.token.refresh}")
    private long REFRESH_TOKEN_TTL;
    private final JwtUtils jwtUtil;

    @Override
    public String extractUsername(String token) {
        log.info("**String, Jwt service extract username from given token!**");
        return jwtUtil.extractUsername(token);
    }

    @Override
    public Date extractExpiration(String token) {
        log.info("**Date, Jwt service extract expiration from given token!**");
        return jwtUtil.extractExpiration(token);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("**T, Jwt service extract claims from given token and claimResolver Function!**");
        return jwtUtil.extractClaim(token, claimsResolver);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("**String, Jwt service generate access token from given userdetails!**");
        return jwtUtil.generateToken(userDetails, ACCESS_TOKEN_TTL);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        log.info("**String, Jwt service generate refresh token from given userdetails!**");
        return jwtUtil.generateToken(userDetails, REFRESH_TOKEN_TTL);
    }

    @Override
    public boolean validateToken(String token, UserDetails user) {
        log.info("**Boolean, Jwt service validate token from given token and userdetail**");
        return jwtUtil.validateToken(token, user);
    }
}
