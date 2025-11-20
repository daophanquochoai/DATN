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
@Entity(name = "rooms")
public class Rooms extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private UUID roomId;
    @Column(unique = true, nullable = true)
    private String name;
    private String location;

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL)
    private List<Employees> employees;
}
