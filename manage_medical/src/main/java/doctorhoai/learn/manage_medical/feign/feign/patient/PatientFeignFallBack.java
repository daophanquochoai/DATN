package doctorhoai.learn.manage_medical.feign.feign.patient;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.patient.PatientsFilter;
import doctorhoai.learn.manage_medical.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientFeignFallBack implements FallbackFactory<PatientFeign> {

    private final HandleFallBack fallBack;

    @Override
    public PatientFeign create(Throwable cause) {
        return new PatientFeign() {
            @Override
            public ResponseEntity<ResponseObject> getPatientsById(UUID id, AccountStatus status) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getPatientByIds(PatientsFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllPatients(String search, List<UUID> ids) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
