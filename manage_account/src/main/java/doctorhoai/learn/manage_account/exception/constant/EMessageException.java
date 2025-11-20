package doctorhoai.learn.manage_account.exception.constant;

import lombok.Getter;

@Getter
public enum EMessageException {
    ROLE_NOT_FOUND("Không tìm thấy vai trò"),
    EMPLOYEE_NOT_FOUND("Không tìm thấy nhân viên"),
    DEPARTMENT_NOT_FOUND("Không tìm thấy phòng ban"),
    PATIENT_NOT_FOUND("Không tìm thấy bệnh nhân"),
    SERVICE_NOT_FOUND("Không tìm thấy dịch vụ"),
    ROOM_NOT_FOUND("Không tìm thấy phòng khám"),
    SPECIALIZATION_NOT_FOUND("Không tìm thấy chuyên khoa"),
    PASSWORD_NOT_CORRECT("Mật khẩu không chính xác"),
    SHIFT_NOT_FOUND("Ca không tìm thấy?")
    ;
    private final String message;
    private EMessageException(String message) {
        this.message = message;
    }
}
