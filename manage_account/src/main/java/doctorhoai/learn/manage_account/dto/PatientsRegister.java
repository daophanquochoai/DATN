package doctorhoai.learn.manage_account.dto;

import doctorhoai.learn.manage_account.dto.constraint.Adult.Adult;
import doctorhoai.learn.manage_account.dto.groupvalidate.CreatePatient;
import doctorhoai.learn.manage_account.dto.groupvalidate.UpdatePatient;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientsRegister {
    @NotNull(message = "Họ tên không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Họ tên không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private String fullName;

    @NotNull(message = "Ngày sinh là bắt buộc", groups = {CreatePatient.class, UpdatePatient.class})
    @Past(message = "Ngày sinh phải nhỏ hơn ngày hiện tại", groups = {CreatePatient.class, UpdatePatient.class})
    @Adult(message = "Bệnh nhân phải đủ 18 tuổi")
    private LocalDate dob;

    @NotNull(message = "Giới tính không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private Boolean gender;

    @NotNull(message = "Địa chỉ không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Địa chỉ không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private String address;

    @NotNull(message = "Mã BHYT không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Mã BHYT không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private String insuranceCode;

    @NotNull(message = "Số liên hệ khẩn cấp không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Số liên hệ khẩn cấp không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private String emergencyContact;

    @NotNull(message = "CCCD không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "CCCD không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @Pattern(
            regexp = "^(\\d{9}|\\d{12})$",
            message = "CCCD phải gồm 9 hoặc 12 chữ số",
            groups = {CreatePatient.class, UpdatePatient.class})
    private String citizenId;

    @NotNull(message = "Nghề nghiệp không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Nghề nghiệp không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private String job;

    // -------------------- ACCOUNT INFO --------------------
    @NotBlank(message = "Số điện thoại không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @Pattern(
            regexp = "^(0\\d{9})$",
            message = "Số điện thoại phải gồm 10 số và bắt đầu bằng 0",
            groups = {CreatePatient.class, UpdatePatient.class})
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email không đúng định dạng",
            groups = {CreatePatient.class, UpdatePatient.class})
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống", groups = {CreatePatient.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Mật khẩu phải tối thiểu 8 ký tự, gồm 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt",
            groups = {CreatePatient.class})
    private String password;

    @NotNull(message = "Trạng thái tài khoản không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private AccountStatus status;

    @NotNull(message = "Vai trò không được để trống", groups = {CreatePatient.class, UpdatePatient.class})
    private UUID roleId;
}
