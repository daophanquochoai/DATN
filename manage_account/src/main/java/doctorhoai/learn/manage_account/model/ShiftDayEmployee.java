package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift_day_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftDayEmployee extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shift_day_employee_id")
    private UUID shiftDayEmployeeId;
    @JoinColumn(name = "employee_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Employees employees;
    private LocalDate date;
    private Integer order;

    @OneToMany(mappedBy = "shiftDayEmployee", cascade = CascadeType.ALL)
    private List<ShiftEmployee> shiftEmployees;
}
