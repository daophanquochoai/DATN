package doctorhoai.learn.indentity_service.exception.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.base_domain.exception.Duplicate;
import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.base_domain.exception.ResponseException;
import doctorhoai.learn.indentity_service.exception.FeignCustomException;
import doctorhoai.learn.indentity_service.exception.UnAuthorizedException;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHandle extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseException.builder()
                        .message(fieldErrors.get(0).getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler({
            FeignException.class,
            FeignException.FeignServerException.class,
            FeignException.FeignClientException.class,
            ExecutionException.class
    })
    public <T extends FeignException> ResponseEntity<ResponseException> handleProxyException( final T e){
        log.info("** Api Exception Handler controller, handle feign proxy exception**");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ResponseException.builder()
                        .message("** Service Down!")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


    @ExceptionHandler({
            FeignCustomException.class
    })
    public <T extends FeignCustomException> ResponseEntity<ResponseException> handleFeignCustomeException( final T e) throws JsonProcessingException {
        log.info("** Api Exception Handler controller, handle feign FeignCustomException exception**");
            String responseBody = e.getResponseBody();

            // Bước 1: Parse lần 1 để remove quotes ngoài
            ResponseException errorResponse  = objectMapper.readValue(responseBody, ResponseException.class);

        return ResponseEntity.status(e.status()).body(
               errorResponse
        );
    }

    @ExceptionHandler(exception = {
            UnAuthorizedException.class,
            BadRequestException.class,
            ExpiredJwtException.class,
            BadCredentialsException.class,
            MalformedJwtException.class,
            DisabledException.class,
            UsernameNotFoundException.class
    })
    public <T extends RuntimeException>ResponseEntity<ResponseException> handleApiRequestException( final T e){
        log.info("**ApiExceptionHandler controller, handle Api request**");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseException.builder()
                        .message("Bad Credentials")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    @ExceptionHandler(value = {Duplicate.class})
    public ResponseEntity<ResponseException> handleDuplicate(Duplicate exception) {
        log.info("**ApiExceptionHandler controller, handle duplicate**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @ExceptionHandler(value = {BadException.class})
    public ResponseEntity<ResponseException> handleBadException(BadException exception) {
        log.info("**ApiExceptionHandler controller, handle BadException**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<ResponseException> handleNotFound(NotFound exception) {
        log.info("**ApiExceptionHandler controller, handle notfound**");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(
            value = {AuthorizationDeniedException.class}
    )
    public ResponseEntity<ResponseException> handleAuthorizationDeniedExceptioN(Exception e){
        return ResponseEntity.status(401).body(
                ResponseException.builder()
                        .message("Access Denied")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception exception) {
        log.info("**ApiExceptionHandler controller, handle exception**");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseException.builder()
                        .message("Error")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
