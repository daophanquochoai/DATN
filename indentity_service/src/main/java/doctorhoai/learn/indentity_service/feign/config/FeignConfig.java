package doctorhoai.learn.indentity_service.feign.config;

import doctorhoai.learn.indentity_service.exception.FeignCustomException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();

                    // Lấy IP từ request gốc
                    String ipAddress = request.getHeader("X-Forwarded-For");

                    if (ipAddress == null || ipAddress.isEmpty()) {
                        // Fallback: lấy từ X-Real-IP
                        ipAddress = request.getHeader("X-Real-IP");
                    }

                    if (ipAddress == null || ipAddress.isEmpty()) {
                        // Fallback cuối cùng: lấy remote address
                        ipAddress = request.getRemoteAddr();
                    }

                    // Thêm header vào Feign request
                    template.header("X-Forwarded-For", ipAddress);

                    log.debug("Feign request - Added X-Forwarded-For: {}", ipAddress);

                    // Bonus: Copy Authorization header nếu có
                    String authorization = request.getHeader("Authorization");
                    if (authorization != null && !authorization.isEmpty()) {
                        template.header("Authorization", authorization);
                        log.debug("Feign request - Added Authorization header");
                    }
                } else {
                    log.warn("ServletRequestAttributes is null - cannot get request headers");
                }
            }
        };
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    public static class CustomErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            String responseBody = null;

            try {
                // Đọc response body
                if (response.body() != null) {
                    responseBody = new String(
                            response.body().asInputStream().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                    log.error("Feign error response body: {}", responseBody);
                }
            } catch (IOException e) {
                log.error("Error reading response body", e);
            }

            // Trigger fallback cho tất cả HTTP errors (4xx, 5xx)
            if (response.status() >= 400) {
                return new FeignCustomException(
                        response.status(),
                        response.reason(),
                        responseBody,
                        response.request()
                );
            }

            return defaultDecoder.decode(methodKey, response);
        }
    }
}