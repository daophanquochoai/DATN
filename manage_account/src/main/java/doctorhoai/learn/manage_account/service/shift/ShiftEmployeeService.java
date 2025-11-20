package doctorhoai.learn.manage_account.service.shift;

import doctorhoai.learn.manage_account.dto.ShiftEmployeeCreate;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ShiftEmployeeFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ShiftEmployeeService {
    List<ShiftEmployeeDto> createShiftEmployee(ShiftEmployeeCreate create);
    PageObject getShiftEmployee(ShiftEmployeeFilter filter);
    ShiftEmployeeDto getShiftEmployee(UUID shiftId);
    List<ShiftEmployeeDto> getShiftEmployeeByIds(List<UUID> ids);
    List<ShiftEmployeeDto> getShiftEmployeeByEmployeeIds(List<UUID> ids, LocalDate date);
    List<ShiftEmployeeDto> getShiftEmployeeByEmployeeId(UUID id);
    void removeShiftIfNotBook(UUID id);
}
