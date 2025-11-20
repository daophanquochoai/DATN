package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authPayment",
        path = "/api/payment",
        fallbackFactory = PaymentFeignFallback.class,
        configuration = FeignConfig.class
)
public interface PaymentFeign {
    @PostMapping("/create")
    ResponseEntity<?> createPayment(
            @RequestBody PaymentRequest request,
            @RequestParam String ipAddress);

    @GetMapping("/ipn/{appointmentId}")
    ResponseEntity<?> verifyPaymentGet(
            @RequestParam Map<String, String> params,
            @PathVariable UUID appointmentId
    );
}
