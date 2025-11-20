package doctorhoai.learn.manage_medical.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "meal_relations")
public class MealRelation extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "relations_id")
    private UUID relationsId;
    private String name;
    private String description;
}
