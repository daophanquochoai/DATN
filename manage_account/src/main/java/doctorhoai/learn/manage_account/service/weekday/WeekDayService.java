package doctorhoai.learn.manage_account.service.weekday;

import doctorhoai.learn.manage_account.dto.WeekDayDto;
import doctorhoai.learn.manage_account.dto.WeekGroupDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.WeekDayFilter;
import doctorhoai.learn.manage_account.dto.request.WeekDayCreate;

import java.util.List;
import java.util.UUID;

public interface WeekDayService {
    WeekGroupDto createWeekDay(WeekDayCreate weekDayCreate);
    PageObject getWeekDayFilter(WeekDayFilter filter);
    WeekGroupDto getLatestGroupByEmployeeId(int group, UUID employeeId);
}
