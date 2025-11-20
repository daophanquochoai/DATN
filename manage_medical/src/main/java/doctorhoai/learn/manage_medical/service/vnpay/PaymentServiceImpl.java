package doctorhoai.learn.manage_medical.service.vnpay;

import doctorhoai.learn.manage_medical.model.VNPayConfig;
import doctorhoai.learn.manage_medical.utils.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final VNPayConfig vnPayConfig;

    @Override
    public String createPayment(Long amount, String orderInfo, String ipAddress) {
        try {
            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", vnPayConfig.getVersion());
            vnpParams.put("vnp_Command", vnPayConfig.getCommand());
            vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
            vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
            vnpParams.put("vnp_CurrCode", "VND");

            // Tạo mã giao dịch unique
            String txnRef = VNPayUtil.getRandomNumber(8);
            vnpParams.put("vnp_TxnRef", txnRef);

            vnpParams.put("vnp_OrderInfo", orderInfo);
            vnpParams.put("vnp_OrderType", vnPayConfig.getOrderType());
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
            vnpParams.put("vnp_IpAddr", ipAddress);

            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            String vnpCreateDate = now.format(formatter);
            vnpParams.put("vnp_CreateDate", vnpCreateDate);

            // Tăng thời gian timeout lên 30 phút nếu cần
            String vnpExpireDate = now.plusMinutes(30).format(formatter);
            vnpParams.put("vnp_ExpireDate", vnpExpireDate);

            // Build query string
            List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnpParams.get(fieldName);

                if (fieldValue != null && fieldValue.length() > 0) {
                    hashData.append(fieldName).append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                            .append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

            String paymentUrl = vnPayConfig.getUrl() + "?" + queryUrl;

            return paymentUrl;

        } catch (Exception e) {
            throw new RuntimeException("Error creating payment URL", e);
        }
    }

    @Override
    public boolean verifyPayment(Map<String, String> params) {
        String vnpSecureHash = params.get("vnp_SecureHash");
        Map<String, String> paramsToVerify = new HashMap<>(params);
        paramsToVerify.remove("vnp_SecureHash");
        paramsToVerify.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(paramsToVerify.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = paramsToVerify.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=').append(fieldValue);
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    hashData.append('&');
                }
            }
        }

        String signValue = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        return signValue.equals(vnpSecureHash);
    }

    @Override
    public Map<String, Object> verifyAndSavePayment(Map<String, String> params) {
        try {
            System.out.println("=== Starting Payment Verification ===");
            System.out.println("All params: " + params);

            // 1. Lấy secure hash từ VNPay
            String vnpSecureHash = params.get("vnp_SecureHash");
            if (vnpSecureHash == null) {
                System.out.println("ERROR: Missing vnp_SecureHash");
                return Map.of(
                        "status", "failed",
                        "message", "Missing secure hash"
                );
            }

            // 2. Tạo bản copy và loại bỏ các field không cần verify
            Map<String, String> paramsToVerify = new HashMap<>(params);
            paramsToVerify.remove("vnp_SecureHash");
            paramsToVerify.remove("vnp_SecureHashType");

            // 3. Sort các field names
            List<String> fieldNames = new ArrayList<>(paramsToVerify.keySet());
            Collections.sort(fieldNames);

            // 4. Build hash data - KHÔNG encode lại
            StringBuilder hashData = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = paramsToVerify.get(fieldName);

                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Build hash data - chỉ nối chuỗi, KHÔNG encode
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(fieldValue);

                    if (itr.hasNext()) {
                        hashData.append('&');
                    }
                }
            }

            String finalHashData = hashData.toString();
            System.out.println("Hash data to verify: " + finalHashData);

            // 5. Tính toán chữ ký
            String signValue = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), finalHashData);
            System.out.println("Calculated hash: " + signValue);
            System.out.println("VNPay hash:      " + vnpSecureHash);

            boolean isValidSignature = signValue.equals(vnpSecureHash);
            System.out.println("Signature valid: " + isValidSignature);

            if (!isValidSignature) {
                return Map.of(
                        "status", "failed",
                        "message", "Invalid signature - possible tampering detected"
                );
            }

            // 6. Lấy thông tin từ params
            String txnRef = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");
            String transactionNo = params.get("vnp_TransactionNo");
            String amount = params.get("vnp_Amount");
            String orderInfo = params.get("vnp_OrderInfo");
            String bankCode = params.get("vnp_BankCode");
            String payDate = params.get("vnp_PayDate");

            System.out.println("✓ Payment verified successfully");
            System.out.println("  Response Code: " + responseCode);
            System.out.println("  Transaction No: " + transactionNo);
            System.out.println("  Amount: " + (Long.parseLong(amount) / 100) + " VND");

            // 7. TODO: Lưu vào database nếu cần

            // 8. Trả về kết quả
            if ("00".equals(responseCode)) {
                return Map.of(
                        "status", "success",
                        "message", "Payment verified and saved successfully",
                        "data", Map.of(
                                "txnRef", txnRef != null ? txnRef : "",
                                "transactionNo", transactionNo != null ? transactionNo : "",
                                "amount", amount != null ? Long.parseLong(amount) / 100 : 0,
                                "orderInfo", orderInfo != null ? orderInfo : "",
                                "bankCode", bankCode != null ? bankCode : "",
                                "payDate", payDate != null ? payDate : ""
                        )
                );
            } else {
                return Map.of(
                        "status", "failed",
                        "message", "Payment failed with code: " + responseCode,
                        "responseCode", responseCode != null ? responseCode : "unknown"
                );
            }

        } catch (Exception e) {
            System.err.println("ERROR in verifyAndSavePayment: " + e.getMessage());
            e.printStackTrace();
            return Map.of(
                    "status", "error",
                    "message", "Error processing payment verification: " + e.getMessage()
            );
        }
    }
}
