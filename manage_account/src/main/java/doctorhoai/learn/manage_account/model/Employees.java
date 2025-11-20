package doctorhoai.learn.manage_account.model;

import doctorhoai.learn.manage_account.model.Account.Account;
import doctorhoai.learn.manage_account.service.employee.EmployeeService;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "employees")
public class Employees extends BaseModel implements Serializable {
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id")
    private UUID employeeId;
    @JoinColumn(name = "account_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account accountId;
    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Rooms roomId;
    @Column(name = "fullname",nullable = false)
    private String fullName;
    @Column(name = "citizen_id", nullable = false)
    private String citizenId;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "gender")
    private boolean gender;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String avatar;
    private String profile;
    @JoinColumn(name = "specialization_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Specializations specializationId;
    @Column(name = "hired_date", nullable = false)
    private LocalDate hiredDate;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "employees", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeServices> employeeServices;
}
