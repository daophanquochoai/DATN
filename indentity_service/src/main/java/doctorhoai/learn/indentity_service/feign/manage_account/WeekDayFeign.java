package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayCreate;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authWeekDayFeign",
        path = "/weekdays",
        fallbackFactory = EmployeeFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface WeekDayFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createWeekDay(
            @RequestBody WeekDayCreate weekDayCreate
    );

    @PostMapping("/filter")
    ResponseEntity<ResponseObject> getWeekDayByFilter(
            @RequestBody WeekDayFilter filter
    );
    @GetMapping("/group/{group}/{employeeId}")
    public ResponseEntity<ResponseObject> getWeekDaysByGroup(
            @PathVariable("group") int group,
            @PathVariable("employeeId") UUID employeeId
    );
}
