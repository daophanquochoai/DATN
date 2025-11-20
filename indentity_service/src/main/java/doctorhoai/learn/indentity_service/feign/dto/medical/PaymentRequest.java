package doctorhoai.learn.indentity_service.feign.dto.medical;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRequest implements Serializable {

    private final static long seriableVersionId = 1L;

    private Long amount;
    private String orderInfo;
}