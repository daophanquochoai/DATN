package doctorhoai.learn.manage_medical.feign.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.base_domain.exception.Duplicate;
import doctorhoai.learn.base_domain.exception.ErrorException;
import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.base_domain.exception.ResponseException;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HandleFallBack {
    public ResponseEntity<ResponseObject> processFallback(Throwable cause){
        try {
            if (cause instanceof FeignException fe) {
                int status = fe.status();
                String json = fe.contentUTF8();
                ObjectMapper objectMapper = new ObjectMapper();
                ResponseException responseException = objectMapper.readValue(json, ResponseException.class);
                switch (status) {
                    case 404:
                        throw new NotFound(responseException.getMessage());
                    case 400:
                        throw new Duplicate(responseException.getMessage());
                }
            }
            throw new ErrorException("** Service downed !");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(cause.getMessage());
        }
    }
}
