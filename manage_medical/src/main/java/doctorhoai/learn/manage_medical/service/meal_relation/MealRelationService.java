package doctorhoai.learn.manage_medical.service.meal_relation;

import doctorhoai.learn.manage_medical.dto.MealRelationDto;
import doctorhoai.learn.manage_medical.dto.filter.MealRelationFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;

import java.util.UUID;

public interface MealRelationService {
    MealRelationDto createMealRelation(MealRelationDto mealRelationDto);
    MealRelationDto getMealRelationById(UUID id);
    MealRelationDto updateMealRelation(UUID id, MealRelationDto mealRelationDto);
    PageObject getAllMealRelation(MealRelationFilter filter);
    void deleteMealRelationById(UUID id);
}
