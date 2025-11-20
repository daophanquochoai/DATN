package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentRecordCreate;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentRecordFeignFallback implements FallbackFactory<AppointmentRecordFeign> {

    private final HandleFallBack fallBack;

    @Override
    public AppointmentRecordFeign create(Throwable cause) {
        return new AppointmentRecordFeign() {
            @Override
            public ResponseEntity<ResponseObject> createAppointmentRecord(AppointmentRecordCreate dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentRecordById(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
