package doctorhoai.learn.indentity_service.business.sms;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.manage_account.PatientFeign;
import doctorhoai.learn.indentity_service.util.FunctionCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final PatientFeign patientFeign;
    private final RedisTemplate<String, String> redisTemplate;
    private RestTemplate restTemplate = new RestTemplate();

    private final FunctionCommon functionCommon;

    @GetMapping("/{phone}")
    public ResponseEntity<?> sendSms(
            @PathVariable String phone
    ){

            ResponseEntity<Boolean> response = patientFeign.checkNumberPhone(phone);
            if( response.getStatusCode().is2xxSuccessful()){
                if( !response.getBody()){
                    return ResponseEntity.status(400).body(
                            ResponseObject.builder()
                                    .message("Số điện thoại đã tồn tại ")
                                    .build()
                    );
                }

                String opt =  functionCommon.randomOpt(4);
                boolean check = functionCommon.sendSmsWithContent("Mã xác thực của bạn là " + opt, phone);
                if( !check){
                    return ResponseEntity.status(400).body(
                            ResponseObject.builder()
                                    .message("Hệ thống đang bận")
                                    .build()
                    );
                }

                System.out.println("OPT : " + opt);
                redisTemplate.opsForValue().set(phone, opt , Duration.ofMinutes(2));
                return ResponseEntity.ok(
                        ResponseObject.builder()
                                .build()
                );
            }else {
                return ResponseEntity.status(400).body(
                        ResponseObject.builder()
                                .message("Number phone already exists")
                                .build()
                );
            }
    }

    @GetMapping("/verify/{opt}/{phone}")
    public ResponseEntity<?> verify(
            @PathVariable String opt,
            @PathVariable String phone
    ){
        String optSaved = redisTemplate.opsForValue().get(phone);
        if( optSaved.equals(opt)){
            redisTemplate.delete(phone);
            String code =  functionCommon.randomOpt(6);
            System.out.println("OPT : " + code);
            redisTemplate.opsForValue().set(phone, code , Duration.ofMinutes(20));
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(code)
                            .build()
            );
        }else{
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Can't verify")
                            .build()
            );
        }
    }
}
