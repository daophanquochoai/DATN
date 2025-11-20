package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayCreate;
import doctorhoai.learn.indentity_service.feign.dto.WeekDayFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WeekDatFeignFallback implements FallbackFactory<WeekDayFeign> {

    private final HandleFallBack fallBack;

    @Override
    public WeekDayFeign create(Throwable cause) {
        return new WeekDayFeign() {
            @Override
            public ResponseEntity<ResponseObject> createWeekDay(WeekDayCreate weekDayCreate) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getWeekDayByFilter(WeekDayFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getWeekDaysByGroup(int group, UUID employeeId) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
