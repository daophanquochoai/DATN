package doctorhoai.learn.indentity_service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class FunctionCommon {


    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.secret}")
    private String apiSecret;

    public String randomOpt(int length) {
        String numbers = "0123456789";
        StringBuilder opt = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * numbers.length());
            opt.append(numbers.charAt(index));
        }
        return opt.toString();
    }

    public boolean sendSmsWithContent( String content, String phone){
        try {
            String encodedContent = URLEncoder.encode(content, StandardCharsets.UTF_8);

            String url = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_get"
                    + "?Content=" + encodedContent
                    + "&SmsType=8"
                    + "&Phone=" + phone
                    + "&ApiKey=" + apiKey
                    + "&SecretKey=" + apiSecret
                    + "&IsUnicode=true";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if( response.statusCode() == 200){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
