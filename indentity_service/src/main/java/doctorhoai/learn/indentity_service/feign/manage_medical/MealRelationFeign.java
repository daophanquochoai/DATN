package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authMealRelation",
        path = "/meal_relation",
        fallbackFactory = MealRelationFeignFallback.class,
        configuration = FeignConfig.class
)
public interface MealRelationFeign {
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createMealRelation(
            @Valid @RequestBody MealRelationDto dto
    );

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getMealRelationById(
            @PathVariable("id") UUID id
    );

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getListMealRelation(
            @RequestBody MealRelationFilter filter
    );

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateMealRelation(
            @PathVariable("id") UUID id,
            @Valid @RequestBody MealRelationDto dto
    );

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteMealRelation(
            @PathVariable("id") UUID id
    ) ;
}
