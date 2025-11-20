package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class UnitNotFoundException extends NotFound {
    public UnitNotFoundException() {
        super(EMessageException.UNIT_NOT_FOUND.getMessage());
    }
}
