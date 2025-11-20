package doctorhoai.learn.manage_medical.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRequest implements Serializable {

    private final static long seriableVersionId = 1L;

    private Long amount;
    private String orderInfo;
}
