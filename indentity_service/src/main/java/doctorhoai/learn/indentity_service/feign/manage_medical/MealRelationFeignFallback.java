package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MealRelationFeignFallback implements FallbackFactory<MealRelationFeign> {

    private final HandleFallBack fallBack;

    @Override
    public MealRelationFeign create(Throwable cause) {
        return new MealRelationFeign() {
            @Override
            public ResponseEntity<ResponseObject> createMealRelation(MealRelationDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getMealRelationById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getListMealRelation(MealRelationFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateMealRelation(UUID id, MealRelationDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteMealRelation(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
