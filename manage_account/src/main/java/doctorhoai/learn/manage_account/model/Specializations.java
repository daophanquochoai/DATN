package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "specializations")
public class Specializations extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "specialization_id")
    private UUID specializationId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "specializationId", cascade = CascadeType.ALL)
    private List<Employees> employees;
}
