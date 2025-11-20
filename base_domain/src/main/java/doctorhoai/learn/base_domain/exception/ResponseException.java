package doctorhoai.learn.base_domain.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseException implements Serializable {
    private static final long serialVersionId = 1L;
    private String message;
    private LocalDateTime timestamp;
}
