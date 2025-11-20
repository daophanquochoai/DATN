package doctorhoai.learn.indentity_service.business.payment;

import doctorhoai.learn.indentity_service.feign.dto.medical.PaymentRequest;
import doctorhoai.learn.indentity_service.feign.manage_medical.PaymentFeign;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentFeign paymentFeign;

    @PostMapping("/create")
    ResponseEntity<?> createPayment(
            @RequestBody PaymentRequest request,
            HttpServletRequest httpRequest)
    {
        String ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpRequest.getRemoteAddr();
        }
        String ipK8s = httpRequest.getHeader("X-Real-IP");
        if( ipK8s != null && ipAddress == null){
            ipAddress =  httpRequest.getHeader("X-Real-IP");
        }
        log.info("XFF = {}", httpRequest.getHeader("X-Forwarded-For"));
        log.info("X-Real-IP = {}", httpRequest.getHeader("X-Real-IP"));
        log.info("REMOTE_ADDR = {}", httpRequest.getRemoteAddr());
        log.info("X-FORWARDED-FOR : " + ipAddress);
        return paymentFeign.createPayment(request,ipAddress);
    }

    @GetMapping("/ipn/{appointmentId}")
    ResponseEntity<?> verifyPaymentGet(
            @RequestParam Map<String, String> params,
            @PathVariable UUID appointmentId
    ){
        return paymentFeign.verifyPaymentGet(params,appointmentId);
    }
}
