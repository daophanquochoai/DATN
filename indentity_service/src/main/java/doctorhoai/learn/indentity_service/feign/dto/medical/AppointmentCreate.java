package doctorhoai.learn.indentity_service.feign.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentCreate implements Serializable {
    @NotNull(message = "Bệnh nhân không được để trống")
    private UUID patientId;

    @NotNull(message = "Bác sĩ/nhân viên không được để trống")
    private UUID shiftId;

    @NotNull(message = "Dịch vụ khám không được để trống")
    private UUID serviceId;
    private PaymentDto payments;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private double price;

    private String transactionCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String fullname;

    @Past(message = "Ngày sinh phải ở trong quá khứ")
    private LocalDate dob;

    @NotNull(message = "Giới tính không được để trống")
    private Boolean gender; // ⚠️ nên dùng Boolean thay vì boolean để cho phép validation hoạt động

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Mã bảo hiểm không hợp lệ")
    private String insuranceCode;

    @Size(max = 50, message = "Liên hệ khẩn cấp không được vượt quá 50 ký tự")
    private String emergencyContact;

    @Pattern(regexp = "^[0-9]{9,12}$", message = "Số CCCD/CMND không hợp lệ")
    private String citizenId;

    @Size(max = 100, message = "Tên nghề nghiệp không được vượt quá 100 ký tự")
    private String job;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
}
