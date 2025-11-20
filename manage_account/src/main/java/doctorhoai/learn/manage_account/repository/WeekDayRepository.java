package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeekDayRepository extends JpaRepository<WeekDay, UUID> {

}
