package doctorhoai.learn.manage_account.model.Account;

import doctorhoai.learn.manage_account.model.BaseModel;
import doctorhoai.learn.manage_account.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "accounts")
public class Account extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "role_id")
    private Role roleId;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

}
