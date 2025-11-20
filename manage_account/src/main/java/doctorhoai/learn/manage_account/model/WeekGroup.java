package doctorhoai.learn.manage_account.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "week_group")
public class WeekGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employees employees;
    private Integer count;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<WeekDay> weekDays;
}
