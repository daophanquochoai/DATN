package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "employee_service")
public class EmployeeServices extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID employeeServiceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employees employees;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Services services;
}
