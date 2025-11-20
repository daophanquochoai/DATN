package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity( name = "roles")
public class Role extends BaseModel implements Serializable {
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    @Column(name = "name", unique = true)
    private String nameRole;
    @Column(name = "description")
    private String description;
}
