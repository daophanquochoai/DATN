package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class ShiftNotFoundException extends NotFound {
    public ShiftNotFoundException() {
        super(EMessageException.SHIFT_NOT_FOUND.getMessage());
    }
}
