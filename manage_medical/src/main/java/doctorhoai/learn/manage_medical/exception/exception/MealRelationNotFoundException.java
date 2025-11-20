package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class MealRelationNotFoundException extends NotFound {
    public MealRelationNotFoundException() {
        super(EMessageException.MEAL_RELATION_NOT_FOUND.getMessage());
    }
}
