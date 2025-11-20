package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class DosageTimeNotFoundException extends NotFound {
    public DosageTimeNotFoundException() {
        super(EMessageException.DOSAGE_TIME_NOT_FOUND.getMessage());
    }
}
