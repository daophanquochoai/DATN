package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.feign.dto.medical.PaymentRequest;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentFeignFallback implements FallbackFactory<PaymentFeign> {

    private final HandleFallBack fallBack;

    @Override
    public PaymentFeign create(Throwable cause) {
        return new PaymentFeign() {
            @Override
            public ResponseEntity<?> createPayment(PaymentRequest request, String ipAddress) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<?> verifyPaymentGet(Map<String, String> params, UUID appointmentId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
