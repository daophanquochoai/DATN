package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class EmployeeNotFoundException extends NotFound {
    public EmployeeNotFoundException() {
        super(EMessageException.EMPLOYEE_NOT_FOUND.getMessage());
    }
}
