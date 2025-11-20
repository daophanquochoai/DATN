package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.MealRelationFilter;
import doctorhoai.learn.indentity_service.feign.manage_medical.MealRelationFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("meal_relation")
@RequiredArgsConstructor
public class MealRelationController {

    private final MealRelationFeign mealRelationFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> createMealRelation(
            @Valid @RequestBody MealRelationDto dto
    )
    {
        return mealRelationFeign.createMealRelation(dto);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getMealRelationById(
            @PathVariable("id") UUID id
    )
    {
        return mealRelationFeign.getMealRelationById(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getListMealRelation(
            @RequestBody MealRelationFilter filter
    )
    {
        return mealRelationFeign.getListMealRelation(filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> updateMealRelation(
            @PathVariable("id") UUID id,
            @Valid @RequestBody MealRelationDto dto
    )
    {
        return mealRelationFeign.updateMealRelation(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> deleteMealRelation(
            @PathVariable("id") UUID id
    )
    {
        return mealRelationFeign.deleteMealRelation(id);
    }

}
