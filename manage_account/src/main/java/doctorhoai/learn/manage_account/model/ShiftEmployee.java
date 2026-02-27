package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shift_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftEmployee extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "shift_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Shift shift;
    @Column(name = "patient_slot")
    private Integer patientSlot;

    @JoinColumn(name = "shift_day_employee_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private ShiftDayEmployee shiftDayEmployee;
}
