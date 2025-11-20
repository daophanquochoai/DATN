package doctorhoai.learn.manage_medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "payments")
public class Payments extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    private String paymentMethod;
}
