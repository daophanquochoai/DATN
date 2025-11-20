package doctorhoai.learn.manage_account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject {
    private String message;
    private Object data;

    public ResponseObject(String message) { this.message = message; }
}
