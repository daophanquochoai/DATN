package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.EmployeeServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeServiceRepository extends JpaRepository<EmployeeServices, UUID> {
}
