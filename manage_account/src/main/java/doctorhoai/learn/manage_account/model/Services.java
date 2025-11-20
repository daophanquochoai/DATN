package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "services")
public class Services extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID serviceId;
    private String name;
    private String description;
    private Double price;
    private String image;

    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeServices> employeeServices;
}
