package doctorhoai.learn.manage_account.service.shift;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeCreate;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ShiftEmployeeFilter;
import doctorhoai.learn.manage_account.exception.exception.EmployeeNotFoundException;
import doctorhoai.learn.manage_account.exception.exception.ShiftNotFoundException;
import doctorhoai.learn.manage_account.feign.appointment.AppointmentFeign;
import doctorhoai.learn.manage_account.feign.dto.AppointmentDto;
import doctorhoai.learn.manage_account.feign.dto.AppointmentStatus;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.Employees;
import doctorhoai.learn.manage_account.model.Shift;
import doctorhoai.learn.manage_account.model.ShiftEmployee;
import doctorhoai.learn.manage_account.repository.EmployeesRepository;
import doctorhoai.learn.manage_account.repository.ShiftEmployeeRepository;
import doctorhoai.learn.manage_account.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftEmployeeServiceImpl implements ShiftEmployeeService{

    private final ShiftEmployeeRepository shiftEmployeeRepository;
    private final EmployeesRepository employeesRepository;
    private final ShiftRepository shiftRepository;
    private final Mapper mapper;
    private final AppointmentFeign appointmentFeign;
    private final ObjectMapper objectMapper;


    @Override
    public List<ShiftEmployeeDto> createShiftEmployee(ShiftEmployeeCreate create) {
        Employees employees = employeesRepository.getEmployeesByEmployeeId(create.getEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        if( create.getShiftIds().size() <= 0 ){
            throw new BadException("Lịch khám đang rỗng");
        }
        List<ShiftEmployee> sEmployees = shiftEmployeeRepository.getShiftEmployeeByEmployees_EmployeeIdAndDate(create.getEmployeeId(), create.getShiftIds().get(0).getDate());
        if(!sEmployees.isEmpty()){
            throw new BadException("Lịch đã được tạo");
        }
        List<UUID> idsShift = create.getShiftIds().stream().map( t -> t.getShiftDto().getId()).toList();
        List<Shift> shifts = shiftRepository.getShiftByIds(idsShift);
        List<ShiftEmployee> shiftEmployees = create.getShiftIds().stream().map( t -> {
            ShiftEmployee shiftEmployee = ShiftEmployee.builder()
                    .shift(getShiftById(t.getShiftDto().getId(), shifts))
                    .date(t.getDate())
                    .employees(employees)
                    .patientSlot(t.getPatientSlot())
                    .build();
            return shiftEmployee;
        }).toList();
        List<ShiftEmployee> shiftE = shiftEmployeeRepository.saveAll(shiftEmployees);
        List<ShiftEmployeeDto> shiftEDto = shiftE.stream().map( t -> {
            return ShiftEmployeeDto.builder()
                    .id(t.getId())
                    .date(t.getDate())
                    .shift(mapper.convertToShiftDto(t.getShift()))
                    .patientSlot(t.getPatientSlot())
                    .build();
        }).toList();
        return shiftEDto;
    }

    @Override
    public PageObject getShiftEmployee(ShiftEmployeeFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }
        Page<ShiftEmployee> shiftEmployees = shiftEmployeeRepository.getShiftEmployeeByFilter(
                filter.getEmployeeIds().isEmpty() ? null : filter.getEmployeeIds(),
                filter.getTime(),
                pageable
        );

        Set<UUID> shiftIds = shiftEmployees.getContent().stream().map(ShiftEmployee::getId).collect(Collectors.toSet());

        // call appointment service to get count appointment by shift ids
        ResponseEntity<ResponseObject> responseAppointment = appointmentFeign.getAppointmentCountByShiftIds(shiftIds);
        ResponseObject responseObject = responseAppointment.getBody();
        if( responseObject == null || responseObject.getData() == null){
            throw new BadException("Lỗi khi lấy dữ liệu từ dịch vụ lịch hẹn");
        }
        @SuppressWarnings("unchecked")
        Map<String, Integer> data = (Map<String, Integer>) responseObject.getData();

        return PageObject.builder()
                .totalPage(shiftEmployees.getTotalPages())
                .pageNo(filter.getPageNo())
                .data(shiftEmployees.getContent().stream().map( t -> {
                    return ShiftEmployeeDto.builder()
                            .id(t.getId())
                            .date(t.getDate())
                            .employeeDto(mapper.convertToEmployeeDto(t.getEmployees()))
                            .shift(mapper.convertToShiftDto(t.getShift()))
                            .patientSlot(t.getPatientSlot())
                            .patientSlotBooked(data.getOrDefault(t.getId().toString(), 0))
                            .build();
                }).toList())
                .build();
    }

    @Override
    public ShiftEmployeeDto getShiftEmployee(UUID shiftId) {
        ShiftEmployee shiftEmployee = shiftEmployeeRepository.findById(shiftId).orElseThrow(ShiftNotFoundException::new);
        return ShiftEmployeeDto.builder()
                .id(shiftEmployee.getId())
                .employeeDto(mapper.convertToEmployeeDto(shiftEmployee.getEmployees()))
                .date(shiftEmployee.getDate())
                .shift(mapper.convertToShiftDto(shiftEmployee.getShift()))
                .patientSlot(shiftEmployee.getPatientSlot())
                .build();
    }

    @Override
    public List<ShiftEmployeeDto> getShiftEmployeeByIds(List<UUID> ids) {
        List<ShiftEmployee> shiftEmployees = shiftEmployeeRepository.getShiftEmployeeByIds(ids);
        return shiftEmployees.stream().map( t -> {
            return ShiftEmployeeDto.builder()
                    .id(t.getId())
                    .employeeDto(mapper.convertToEmployeeDto(t.getEmployees()))
                    .date(t.getDate())
                    .shift(mapper.convertToShiftDto(t.getShift()))
                    .patientSlot(t.getPatientSlot())
                    .build();
        }).toList();
    }

    @Override
    public List<ShiftEmployeeDto> getShiftEmployeeByEmployeeIds(List<UUID> ids, LocalDate date) {
        List<ShiftEmployee> shiftEmployees = shiftEmployeeRepository.getShiftEmployeeByEmployeesIds(ids, date);
        return shiftEmployees.stream().map( t -> {
            return ShiftEmployeeDto.builder()
                    .id(t.getId())
                    .employeeDto(mapper.convertToEmployeeDto(t.getEmployees()))
                    .date(t.getDate())
                    .shift(mapper.convertToShiftDto(t.getShift()))
                    .patientSlot(t.getPatientSlot())
                    .build();
        }).toList();
    }

    @Override
    public List<ShiftEmployeeDto> getShiftEmployeeByEmployeeId(UUID id) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<ShiftEmployee> shiftEmployees = shiftEmployeeRepository.getShiftEmployeeByEmployees_EmployeeIdInMonth(id, oneMonthAgo);
        return shiftEmployees.stream().map( t -> {
            return ShiftEmployeeDto.builder()
                    .id(t.getId())
                    .employeeDto(mapper.convertToEmployeeDto(t.getEmployees()))
                    .date(t.getDate())
                    .shift(mapper.convertToShiftDto(t.getShift()))
                    .patientSlot(t.getPatientSlot())
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public void removeShiftIfNotBook(UUID id) {
        ResponseEntity<ResponseObject> appointmentCheck = appointmentFeign.getAppointmentByShiftId(id);
        ResponseObject appointmentCheckObject = appointmentCheck.getBody();
        List<AppointmentDto> appointmentCheckDtos = objectMapper.convertValue(appointmentCheckObject.getData(), new TypeReference<List<AppointmentDto>>() {
        });
        List<AppointmentDto> appointmentCompleteDtos = appointmentCheckDtos.stream().filter(i -> i.getStatus().equals(AppointmentStatus.COMPLETE) || i.getStatus().equals(AppointmentStatus.PAYMENT)).toList();
        if(!appointmentCompleteDtos.isEmpty()){
            throw new BadException("Lịch khám đã được đặt!");
        }

        shiftEmployeeRepository.deleteById(id);
    }

    public Shift getShiftById(UUID id, List<Shift> shifts){
        for( Shift shift : shifts){
            if( shift.getId().equals(id)){
                return shift;
            }
        }
        throw new ShiftNotFoundException();
    }
}
