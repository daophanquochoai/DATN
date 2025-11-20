package doctorhoai.learn.manage_medical.exception.constant;

import lombok.Getter;

@Getter
public enum EMessageException {
    DOSAGE_TIME_NOT_FOUND("Không tìm thấy thời gian dùng thuốc"),
    MEAL_RELATION_NOT_FOUND("Không tìm thấy thông tin liên quan đến bữa ăn"),
    MANUFACTURER_NOT_FOUND("Không tìm thấy nhà sản xuất"),
    UNIT_NOT_FOUND("Không tìm thấy đơn vị tính"),
    DRUG_NOT_FOUND("Không tìm thấy thuốc"),
    EMPLOYEE_NOT_FOUND("Không tìm thấy nhân viên"),
    PATIENT_NOT_FOUND("Không tìm thấy bệnh nhân"),
    SERVICE_NOT_FOUND("Không tìm thấy dịch vụ"),
    APPOINTMENT_NOT_FOUND("Không tìm thấy cuộc hẹn"),
    APPOINTMENT_RECORD_NOT_FOUND("Không tìm thấy hồ sơ cuộc hẹn"),
    FOLLOW_UP_VISIT_NOT_FOUND("Không tìm thấy lịch tái khám"),
    ICD10_NOT_FOUND("Không tìm thấy mã ICD10")
    ;
    private final String message;
    private EMessageException(String message) {
        this.message = message;
    }
}
