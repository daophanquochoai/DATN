package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class PatientsNotFoundException extends NotFound {
    public PatientsNotFoundException() {
        super(EMessageException.PATIENT_NOT_FOUND.getMessage());
    }
}
