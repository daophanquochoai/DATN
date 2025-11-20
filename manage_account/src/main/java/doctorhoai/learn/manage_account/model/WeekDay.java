package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "week_day")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class WeekDay extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID weekDayId;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private WeekGroup group;

    @OneToMany(mappedBy = "weekDay", cascade = CascadeType.ALL)
    private List<WeekDayShift> weekDayShifts;
}
