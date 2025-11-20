package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.EmployeeRegister;
import doctorhoai.learn.indentity_service.feign.dto.EmployeesFilter;
import doctorhoai.learn.indentity_service.feign.dto.UpdatePassword;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
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
            public ResponseEntity<ResponseObject> getEmployeePhoneNumber(String phone, AccountStatus active) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> createAccountEmployee(EmployeeRegister register) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAccountEmployee(UUID id, AccountStatus active) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployees(EmployeesFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateEmployeeById(UUID id, EmployeeRegister register) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeByIds(EmployeesFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteEmployeeById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePassword(UpdatePassword data, UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getEmployeeByUsername(String username) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updatePasswordByOPT(String password, String phone) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> resetPassword(String phone) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
