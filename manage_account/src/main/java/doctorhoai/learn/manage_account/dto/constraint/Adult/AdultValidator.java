package doctorhoai.learn.manage_account.dto.constraint.Adult;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if( date == null){
            return false;
        }
        return Period.between(date, LocalDate.now()).getYears() >= 18;
    }
}
