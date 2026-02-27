package doctorhoai.learn.manage_account.service.employee;

import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.EmployeeRegister;
import doctorhoai.learn.manage_account.dto.UpdatePassword;
import doctorhoai.learn.manage_account.dto.filter.EmployeesFilter;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeRegister register);
    EmployeeDto getEmployeeById(UUID id, AccountStatus status);
    EmployeeDto updateEmployee(UUID id, EmployeeRegister register);
    EmployeeDto getEmployeeByPhone(String phone, AccountStatus status);
    void deleteEmployee(UUID id);
    PageObject getAllEmployees(EmployeesFilter filter);
    List<EmployeeDto> getEmployeeByIds(List<UUID> ids);
    EmployeeDto updatePassword(UUID id, UpdatePassword updatePassword);
    EmployeeDto getEmployeeByUsername(String username);
    EmployeeDto updatePasswordByOPT(String password, String phone);
    EmployeeDto resetPassword(String phone);
    Map<String, Object> getTopEmployees(LocalDate startDate, LocalDate endDate, int topN);
}
