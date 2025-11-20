package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "shift")
public class Shift extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalTime endTime;
    private LocalTime startTime;

}
