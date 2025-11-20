package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class DrugNotFoundException extends NotFound {
    public DrugNotFoundException() {
        super(EMessageException.DRUG_NOT_FOUND.getMessage());
    }
}
