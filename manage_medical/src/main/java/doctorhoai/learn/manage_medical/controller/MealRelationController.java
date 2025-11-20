package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.dto.MealRelationDto;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.MealRelationFilter;
import doctorhoai.learn.manage_medical.service.meal_relation.MealRelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("meal_relation")
@RequiredArgsConstructor
public class MealRelationController {

    private final MealRelationService mealRelationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createMealRelation(
            @Valid @RequestBody MealRelationDto dto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Create meal relation successfully")
                        .data(mealRelationService.createMealRelation(dto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getMealRelationById(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Get meal relation successfully")
                        .data(mealRelationService.getMealRelationById(id))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getListMealRelation(
            @RequestBody MealRelationFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Get list meal relation successfully")
                        .data(mealRelationService.getAllMealRelation(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateMealRelation(
            @PathVariable("id") UUID id,
            @Valid @RequestBody MealRelationDto dto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Update meal relation successfully")
                        .data(mealRelationService.updateMealRelation(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteMealRelation(
            @PathVariable("id") UUID id
            ) {
        mealRelationService.deleteMealRelationById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Delete meal relation successfully")
                        .build()
        );
    }
}
