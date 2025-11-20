package doctorhoai.learn.manage_medical.feign.feign.employee;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.employee.EmployeesFilter;
import doctorhoai.learn.manage_medical.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeFeignFallBack implements FallbackFactory<EmployeeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public EmployeeFeign create(Throwable cause) {
        return new EmployeeFeign() {

            @Override
            public ResponseEntity<ResponseObject> getAccountEmployee(UUID id, AccountStatus active) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeByIds(EmployeesFilter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
