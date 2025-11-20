package doctorhoai.learn.manage_account.feign.config;

import doctorhoai.learn.manage_account.exception.exception.FeignCustomException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FeignConfig {

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