package doctorhoai.learn.manage_medical.service.vnpay;

import java.util.Map;

public interface PaymentService {
    String createPayment(Long amount, String orderInfo, String ipAddress);
    boolean verifyPayment(Map<String, String> params);
    Map<String, Object> verifyAndSavePayment(Map<String, String> params);
}
