package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentCreate;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class AppointmentFeignFallback implements FallbackFactory<AppointmentFeign> {

    private final HandleFallBack fallBack;

    @Override
    public AppointmentFeign create(Throwable cause) {
        return new AppointmentFeign() {
            @Override
            public ResponseEntity<ResponseObject> createAppointment(AppointmentCreate appointmentCreate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentList(AppointmentFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getFollowUpVisits(LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentCountByServiceId(LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentCountByDay(LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getOldAppointment(UUID employeeId, String icd10Id) throws ExecutionException, InterruptedException {
                return fallBack.processFallback(cause);
            }
        };
    }
}
