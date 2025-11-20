package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class SpecializationNotFoundException extends NotFound {
    public SpecializationNotFoundException() {
        super(EMessageException.SPECIALIZATION_NOT_FOUND.getMessage());
    }
}
