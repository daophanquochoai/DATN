package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class AppointmentRecordNotFoundException extends NotFound {
    public AppointmentRecordNotFoundException() {
        super(EMessageException.APPOINTMENT_RECORD_NOT_FOUND.getMessage());
    }
}
