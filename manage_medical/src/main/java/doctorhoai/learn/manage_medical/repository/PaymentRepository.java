package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, UUID> {
}
