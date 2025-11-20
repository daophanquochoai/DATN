package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.WeekDayShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeekDayShiftRepository extends JpaRepository<WeekDayShift, UUID> {
}
