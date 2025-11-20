package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class ICD10NotFoundException extends NotFound {
    public ICD10NotFoundException() {
        super(EMessageException.ICD10_NOT_FOUND.getMessage());
    }
}
