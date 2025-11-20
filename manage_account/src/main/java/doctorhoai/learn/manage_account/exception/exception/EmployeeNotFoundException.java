package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class EmployeeNotFoundException extends NotFound {
    public EmployeeNotFoundException() {
        super(EMessageException.EMPLOYEE_NOT_FOUND.getMessage());
    }
}
