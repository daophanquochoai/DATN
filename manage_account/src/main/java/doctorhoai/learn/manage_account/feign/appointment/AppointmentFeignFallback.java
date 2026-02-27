package doctorhoai.learn.manage_account.feign.appointment;

import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeDto;
import doctorhoai.learn.manage_account.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentFeignFallback implements FallbackFactory<AppointmentFeign> {

    private final HandleFallBack fallBack;

    @Override
    public AppointmentFeign create(Throwable cause) {
        return new AppointmentFeign() {
            @Override
            public ResponseEntity<ResponseObject> checkAppointmentBooked(LocalDate date, UUID patientId, UUID serviceId) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentCountByShiftIds(Set<UUID> shiftIds) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentByPatientAndDateAndServiceAndShift(UUID patientId, UUID serviceId, List<ShiftEmployeeDto> shiftIds) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAppointmentByShiftId(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> removeAppointmentByShiftId(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> countShiftInAppointments(LocalDate startDate, LocalDate endDate) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
