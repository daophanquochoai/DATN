package doctorhoai.learn.indentity_service.feign.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDto implements Serializable {
    private final static long serialVersionId = 1L;
    private UUID paymentId;
    private String paymentMethod;
}
