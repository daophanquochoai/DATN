package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class AppointmentNotFoundException extends NotFound {
    public AppointmentNotFoundException() {
        super(EMessageException.APPOINTMENT_NOT_FOUND.getMessage());
    }
}
