package doctorhoai.learn.indentity_service.controller;

import doctorhoai.learn.indentity_service.dto.request.RequestAuth;
import doctorhoai.learn.indentity_service.dto.response.ResponseAuth;
import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.jwt.service.jwt.JwtService;
import doctorhoai.learn.indentity_service.jwt.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Value(value = "${jwt.token.access}")
    private long ACCESS_TOKEN_TTL;
    @Value(value = "${jwt.token.refresh}")
    private long REFRESH_TOKEN_TTL;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody RequestAuth auth
            )
    {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            if( authentication.isAuthenticated() ){
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String accessToken = jwtService.generateToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);
                tokenService.saveToken(accessToken, userDetails.getUsername(), ACCESS_TOKEN_TTL);
                tokenService.saveToken(refreshToken, userDetails.getUsername(), REFRESH_TOKEN_TTL);
                return ResponseEntity.ok(
                        ResponseAuth.builder()
                                .access_token(accessToken)
                                .refresh_token(refreshToken)
                                .data(userDetails)
                                .build()
                );
            }
            throw new UsernameNotFoundException("Username or password is incorrect");
        }catch (Exception e){
            throw new UsernameNotFoundException("Username or password is incorrect");
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<ResponseObject> logout(
            HttpServletRequest request
    ){
        log.info("** Authentication controller, proceed with the request logout **");
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        tokenService.deleteToken(token);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Logout success!")
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestParam(required = true) String refreshToken
    ) {
        try {

            if (refreshToken == null || refreshToken.isEmpty()) {
                throw new BadCredentialsException("Token không được đính kèm");
            }

            // Extract username from token
            String username = jwtService.extractUsername(refreshToken);
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Check if refresh token exists in storage
            if (!jwtService.validateToken(refreshToken, userDetails)) {
                throw new BadCredentialsException("Token không được xác thực");
            }

            // Generate new access token
            String newAccessToken = jwtService.generateToken(userDetails);

            // Optionally: Generate new refresh token (rotate refresh tokens)
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            // Save new tokens
            tokenService.saveToken(newAccessToken, username, ACCESS_TOKEN_TTL);
            tokenService.saveToken(newRefreshToken, username, REFRESH_TOKEN_TTL);

            // Revoke old refresh token
            tokenService.deleteToken(refreshToken);

            return ResponseEntity.ok(
                    ResponseAuth.builder()
                            .access_token(newAccessToken)
                            .refresh_token(newRefreshToken)
                            .data(userDetails)
                            .build()
            );

        } catch (Exception e) {
            throw new BadCredentialsException("Failed to refresh token: " + e.getMessage());
        }
    }
}
