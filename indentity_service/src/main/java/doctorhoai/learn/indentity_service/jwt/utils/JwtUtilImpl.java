package doctorhoai.learn.indentity_service.jwt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.base_domain.exception.ErrorException;
import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.EmployeeDto;
import doctorhoai.learn.indentity_service.feign.manage_account.EmployeeFeign;
import doctorhoai.learn.indentity_service.feign.manage_account.PatientFeign;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtilImpl implements JwtUtils{

    private static final String SECRET = "HocVienCongNgheBuuChinhVienThongCoSoHoChiMinh";
    private final EmployeeFeign employeeFeign;
    private final PatientFeign patientFeign;
    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @Override
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimFunction) {
        final Claims claims = this.extractAllClaims(token);
        return claimFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    @Override
    public String generateToken(UserDetails userDetails, Long time) {
        final Map<String,Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        ResponseEntity<ResponseObject> data;
        if( authorities.get(0).equals("ROLE_PATIENT")){
            data = patientFeign.getPatientByPhoneNumber(userDetails.getUsername(), null);
            claims.put("data", Objects.requireNonNull(data.getBody()).getData());
        } else {
            data = employeeFeign.getEmployeePhoneNumber(userDetails.getUsername(), null);
            EmployeeDto employeeDto = objectMapper.convertValue(Objects.requireNonNull(data.getBody()).getData(), EmployeeDto.class);
            employeeDto.setAccountId(null); // remove sensitive info
            employeeDto.setProfile(null); // remove sensitive info
            employeeDto.setServiceDto(null); // remove sensitive info
            employeeDto.setSpecialization(null);
            Map<String, Object> dataMap = objectMapper.convertValue(employeeDto, Map.class);

            // Fix LocalDate convert (vì jjwt không serialize LocalDate được)
            if (employeeDto.getDob() != null) {
                dataMap.put("dob", employeeDto.getDob().toString());
            }

            claims.put("data", dataMap);
        }
        if( data.getStatusCode() != HttpStatus.OK){
            log.error("Can't get info account");
            throw new ErrorException("Can't get info account");
        }
        claims.put("roles", authorities);
        return this.createToken(claims, userDetails.getUsername(), time);
    }

    public String createToken( final Map<String, Object> claims, final String subject, Long time){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (
                username.equals(userDetails.getUsername())
        );
    }
}
