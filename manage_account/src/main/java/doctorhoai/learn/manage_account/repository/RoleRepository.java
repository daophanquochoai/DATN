package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleId(UUID roleId);
}
