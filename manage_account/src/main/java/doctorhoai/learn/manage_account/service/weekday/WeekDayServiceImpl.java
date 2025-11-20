package doctorhoai.learn.manage_account.service.weekday;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.ShiftDto;
import doctorhoai.learn.manage_account.dto.WeekDayDto;
import doctorhoai.learn.manage_account.dto.WeekGroupDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.WeekDayFilter;
import doctorhoai.learn.manage_account.dto.request.WeekDayCreate;
import doctorhoai.learn.manage_account.dto.request.WeekDayShiftCreate;
import doctorhoai.learn.manage_account.exception.exception.EmployeeNotFoundException;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.*;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.repository.EmployeesRepository;
import doctorhoai.learn.manage_account.repository.ShiftRepository;
import doctorhoai.learn.manage_account.repository.WeekDayRepository;
import doctorhoai.learn.manage_account.repository.WeekGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeekDayServiceImpl implements WeekDayService{

    private final EmployeesRepository employeesRepository;
    private final ShiftRepository shiftRepository;
    private final WeekDayRepository weekDayRepository;
    private final WeekGroupRepository weekGroupRepository;
    private final Mapper mapper;

    @Override
    @Transactional
    public WeekGroupDto createWeekDay(WeekDayCreate weekDayCreate) {
        Employees employees = employeesRepository.getEmployeesByEmployeeIdAndAccountId_Status(weekDayCreate.getEmployeeId(), AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        List<Shift> shifts = shiftRepository.getShiftByIds(weekDayCreate.getWeekDays().stream().map(WeekDayShiftCreate::getShiftIds).flatMap(List::stream).toList());

        // week group
        WeekGroup weekGroup = WeekGroup.builder()
                .employees(employees)
                .count(1)
                .build();

        Optional<WeekGroup> weekGroupOld = weekGroupRepository.getWeekGroupByEmployeeId(weekDayCreate.getEmployeeId());
        weekGroupOld.ifPresent(group -> weekGroup.setCount(group.getCount()));

        List<WeekDay> weekDayList = new ArrayList<>();
        for(WeekDayShiftCreate weekDayShiftCreate : weekDayCreate.getWeekDays()){
            WeekDay weekDay = WeekDay.builder()
                    .dayOfWeek(weekDayShiftCreate.getDayOfWeek())
                    .group(weekGroup)
                    .build();

            List<WeekDayShift> weekDayShifts = weekDayShiftCreate.getShiftIds().stream().map(shiftId -> {
                WeekDayShift weekDayShift = new WeekDayShift();
                weekDayShift.setShift(getShiftById(shiftId, shifts));
                weekDayShift.setWeekDay(weekDay);
                return weekDayShift;
            }).toList();
            weekDay.setWeekDayShifts(weekDayShifts);
            weekDayList.add(weekDay);
        }

        weekGroup.setWeekDays(weekDayList);
        WeekGroup weekGroupSaved = weekGroupRepository.save(weekGroup);

        //dto
        return mapToWeekGroupDto(weekGroupSaved);
    }

    @Override
    public PageObject getWeekDayFilter(WeekDayFilter filter) {
        Pageable pageable;
        pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.unsorted());
        Page<WeekGroup> weekGroupPage = weekGroupRepository.getWeekGroupByFilter(
                filter.getEmployeeIds(),
                pageable
        );

        List<WeekGroupDto> weekGroupDtos = weekGroupPage.getContent().stream().map(this::mapToWeekGroupDto).toList();
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(weekGroupPage.getTotalPages())
                .data(weekGroupDtos)
                .build();
    }

    @Override
    public WeekGroupDto getLatestGroupByEmployeeId(int group , UUID employeeId) {
        Optional<WeekGroup> weekGroup = weekGroupRepository.getWeekGroupByIdAndEmployees_EmployeeId(group, employeeId);
        if( weekGroup.isEmpty()){
            throw new BadException("Lịch mẫu không tồn tại");
        }
        return mapToWeekGroupDto(weekGroup.get());
    }


    public Shift getShiftById(UUID id, List<Shift> shifts) {
        for( Shift shift : shifts) {
            if(shift.getId().equals(id)) {
                return shift;
            }
        }
        return null;
    }

    public WeekDayDto mapToDto(WeekDay weekDay) {
        WeekDayDto weekDayDto = WeekDayDto.builder()
                .dayOfWeek(weekDay.getDayOfWeek())
                .id(weekDay.getWeekDayId())
                .build();
        List<ShiftDto> shiftDtos = weekDay.getWeekDayShifts().stream()
                .map(weekDayShift -> mapper.convertToShiftDto(weekDayShift.getShift()))
                .toList();
        weekDayDto.setShiftDtos(shiftDtos);
        return weekDayDto;
    }

    public WeekGroupDto mapToWeekGroupDto(WeekGroup weekGroup){
        //dto
        WeekGroupDto weekGroupDto = WeekGroupDto.builder()
                .id(weekGroup.getId())
                .employeeDto(mapper.convertToEmployeeDto(weekGroup.getEmployees()))
                .count(weekGroup.getCount())
                .build();
        List<WeekDayDto> weekDayDtos = weekGroup.getWeekDays().stream().map(this::mapToDto).toList();
        weekGroupDto.setWeekDayDtos(weekDayDtos);
        return weekGroupDto;
    }
}
