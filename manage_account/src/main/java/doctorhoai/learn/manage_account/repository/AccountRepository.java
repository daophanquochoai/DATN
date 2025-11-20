package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByPhoneNumber(String phoneNumber);
}
