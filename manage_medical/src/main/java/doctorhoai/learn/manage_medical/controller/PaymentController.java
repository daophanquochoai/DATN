package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.dto.request.PaymentRequest;
import doctorhoai.learn.manage_medical.service.appointment.AppointmentService;
import doctorhoai.learn.manage_medical.service.vnpay.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(
            @RequestBody PaymentRequest request,
            @RequestParam String ipAddress) {
        log.info("IPAddress : " + ipAddress);
        try {
            String paymentUrl = paymentService.createPayment(
                    request.getAmount(),
                    request.getOrderInfo(),
                    ipAddress
            );
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/ipn/{appointmentId}")
    public ResponseEntity<?> verifyPaymentGet(
            @RequestParam Map<String, String> params,
            @PathVariable UUID appointmentId
            ) {
        System.out.println("=== Payment IPN GET Endpoint Called ===");
        System.out.println("Received params: " + params);
        Map<String, Object> result = paymentService.verifyAndSavePayment(params);

        // Response theo format VNPay yêu cầu
        if ("success".equals(result.get("status"))) {
            appointmentService.paymentAppointment(appointmentId,params.get("vnp_TransactionNo"));
            return ResponseEntity.ok(Map.of(
                    "RspCode", "00",
                    "Message", "Confirm Success"
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                    "RspCode", "97",
                    "Message", "Invalid Signature"
            ));
        }
    }
}