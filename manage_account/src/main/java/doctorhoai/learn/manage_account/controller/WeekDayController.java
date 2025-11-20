package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.filter.WeekDayFilter;
import doctorhoai.learn.manage_account.dto.request.WeekDayCreate;
import doctorhoai.learn.manage_account.service.weekday.WeekDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/weekdays")
@RequiredArgsConstructor
public class WeekDayController {

    private final WeekDayService weekDayService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createWeekDay(
            @RequestBody WeekDayCreate weekDayCreate
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.SAVE_DATA_SUCCESSFUL.getMessage())
                        .data(weekDayService.createWeekDay(weekDayCreate))
                        .build()
        );
    }

    @PostMapping("/filter")
    private ResponseEntity<ResponseObject> getWeekDayByFilter(
            @RequestBody WeekDayFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(weekDayService.getWeekDayFilter(filter))
                        .build()
        );
    }

    @GetMapping("/group/{group}/{employeeId}")
    public ResponseEntity<ResponseObject> getWeekDaysByGroup(
            @PathVariable("group") int group,
            @PathVariable("employeeId") UUID employeeId
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(weekDayService.getLatestGroupByEmployeeId(group, employeeId))
                        .build()
        );
    }
}
