package doctorhoai.learn.indentity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAuth {
    private String access_token;
    private String refresh_token;
    private Object data;
}
