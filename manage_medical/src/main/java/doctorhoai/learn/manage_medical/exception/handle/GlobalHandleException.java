package doctorhoai.learn.manage_medical.exception.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.base_domain.exception.Duplicate;
import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.base_domain.exception.ResponseException;
import doctorhoai.learn.manage_medical.exception.constant.ETypeLog;
import doctorhoai.learn.manage_medical.exception.exception.FeignCustomException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalHandleException extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // import
    private final Log logFunction;

    // handle not found
    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<ResponseException> handleNotFound(NotFound ex) {
        log.info("**ApiExceptionHandler controller, handle notfound**");
        logFunction.logSentry(ex.getMessage(), ETypeLog.ERROR, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseException.builder()
                                .message(ex.getMessage())
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

    // handle validate param
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        logFunction.logSentry(fieldErrors.get(0).getDefaultMessage(), ETypeLog.ERROR, ex);
        log.info("**ApiExceptionHandler controller, handle validate**");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(fieldErrors.get(0).getDefaultMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    // handle duplicate
    @ExceptionHandler(value = {Duplicate.class})
    public ResponseEntity<ResponseException> handleDuplicate(Duplicate exception){
        log.info("**ApiExceptionHandler controller, handle duplicate**");
        logFunction.logSentry(exception.getMessage(), ETypeLog.ERROR, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    // handle bad exception
    @ExceptionHandler(value = {BadException.class})
    public ResponseEntity<ResponseException> handleBadException(BadException exception){
        log.info("**ApiExceptionHandler controller, handle bad exception**");
        logFunction.logSentry(exception.getMessage(), ETypeLog.ERROR, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.info("**ApiExceptionHandler: malformed JSON request**");
        logFunction.logSentry(ex.getMessage(), ETypeLog.ERROR, ex);

        ResponseException response = ResponseException.builder()
                .message("Malformed JSON request: " + ex.getMostSpecificCause().getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {
            FeignCustomException.class
    })
    public <T extends FeignCustomException> ResponseEntity<ResponseException> handleFeignCustomeException(final T e) throws JsonProcessingException {
        log.info("** Api Exception Handler controller, handle feign FeignCustomException exception**");
        String responseBody = e.getResponseBody();

        // Bước 1: Parse lần 1 để remove quotes ngoài
        ResponseException errorResponse  = objectMapper.readValue(responseBody, ResponseException.class);

        return ResponseEntity.status(e.status()).body(
                errorResponse
        );
    }

    @ExceptionHandler({
            CompletionException.class
    })
    public ResponseEntity<ResponseException> handleCompletionException(CompletionException e) throws JsonProcessingException {
        log.error("**Caught CompletionException, unwrapping cause...**");

        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        log.error("**Cause type: {}", cause != null ? cause.getClass().getName() : "null");

        // Unwrap và xử lý cause
        if (cause instanceof FeignCustomException) {
            return handleFeignCustomeException((FeignCustomException) cause);
        }

        if (cause instanceof FeignException) {
            return handleProxyException((FeignException) cause);
        }

        if (cause instanceof BadException) {
            return handleBadException((BadException) cause);
        }

        if (cause instanceof NotFound) {
            return handleNotFound((NotFound) cause);
        }

        // Fallback
        logFunction.logSentry(e.getMessage(), ETypeLog.ERROR, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseException.builder()
                        .message(cause != null ? cause.getMessage() : e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // handle exception
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseException> handleException(Exception exception){
        log.info("**ApiExceptionHandler controller, handle exception**");
        logFunction.logSentry(exception.getMessage(), ETypeLog.ERROR, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseException.builder()
                                .message(exception.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
}
