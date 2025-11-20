package doctorhoai.learn.manage_medical.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
@Data
public class VNPayConfig {
    private String url;
    private String returnUrl;
    private String tmnCode;
    private String ipnUrl;
    private String secretKey;
    private String version;
    private String command;
    private String orderType;
}