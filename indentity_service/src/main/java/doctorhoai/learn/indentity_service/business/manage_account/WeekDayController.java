package doctorhoai.learn.indentity_service.business.manage_account;


import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayCreate;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayFilter;
import doctorhoai.learn.indentity_service.feign.manage_account.WeekDayFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/weekdays")
@RequiredArgsConstructor
public class WeekDayController {

    private final WeekDayFeign weekDayFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> createWeekDay(
            @RequestBody WeekDayCreate weekDayCreate
    ){
        return weekDayFeign.createWeekDay(weekDayCreate);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getWeekDayByFilter(
            @RequestBody WeekDayFilter filter
    ){
        return weekDayFeign.getWeekDayByFilter(filter);
    }

    @GetMapping("/group/{group}/{employeeId}")
    public ResponseEntity<ResponseObject> getWeekDaysByGroup(
            @PathVariable("group") int group,
            @PathVariable("employeeId") UUID employeeId
    )
    {
        return weekDayFeign.getWeekDaysByGroup(group, employeeId);
    }
}
