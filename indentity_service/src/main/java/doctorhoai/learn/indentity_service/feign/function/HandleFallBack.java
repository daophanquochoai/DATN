package doctorhoai.learn.indentity_service.feign.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.exception.FeignCustomException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleFallBack {

    private final ObjectMapper objectMapper;

    public ResponseEntity<ResponseObject> processFallback(Throwable cause) {
        log.error("Fallback triggered: {}", cause.getMessage());

        // Xử lý custom exception với response body
        if (cause instanceof FeignCustomException customEx) {
            log.error("Feign error status: {}, body: {}",
                    customEx.getStatus(), customEx.getResponseBody());

            try {
                // Parse response body từ server
                if (customEx.getResponseBody() != null) {
                    ResponseObject errorResponse = objectMapper.readValue(
                            customEx.getResponseBody(),
                            ResponseObject.class
                    );

                    return ResponseEntity
                            .status(customEx.getStatus())
                            .body(errorResponse);
                }
            } catch (Exception e) {
                log.error("Error parsing response body", e);
            }

            // Fallback mặc định nếu không parse được
            return ResponseEntity
                    .status(customEx.getStatus())
                    .body(ResponseObject.builder()
                            .message("Error from service: " + customEx.getMessage())
                            .build());
        }

        // Xử lý các exception khác (timeout, connection refused...)
        if (cause instanceof FeignException feignEx) {
            log.error("FeignException: status={}, message={}",
                    feignEx.status(), feignEx.getMessage());

            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ResponseObject.builder()
                            .message("Service temporarily unavailable")
                            .build());
        }

        // Default fallback
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseObject.builder()
                        .message("An unexpected error occurred")
                        .build());
    }
}