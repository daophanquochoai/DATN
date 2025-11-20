package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.PatientsFilter;
import doctorhoai.learn.indentity_service.feign.dto.PatientsRegister;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientFeignFallBack implements FallbackFactory<PatientFeign> {

    private final HandleFallBack fallBack;

    @Override
    public PatientFeign create(Throwable cause) {
        return new PatientFeign() {
            @Override
            public ResponseEntity<ResponseObject> getPatientByPhoneNumber(String phone, AccountStatus status) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> createAccountPatient(PatientsRegister register) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<?> getPatientsById(UUID id, AccountStatus status) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getPatientsList(PatientsFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePatients(UUID id, PatientsRegister register) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getPatientByIds(PatientsFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<Boolean> checkNumberPhone(String phone) {
                return ResponseEntity.ok(
                        false
                );
            }

            @Override
            public ResponseEntity<ResponseObject> updatePassword(UUID id, PatientsRegister register) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getPatientByUsername(String username) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePasswordByOpt(String phone, String password) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
