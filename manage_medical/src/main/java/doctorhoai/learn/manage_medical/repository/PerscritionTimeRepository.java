package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.PerscriptionTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerscritionTimeRepository extends JpaRepository<PerscriptionTime, UUID> {
    List<PerscriptionTime> getPerscriptionTimeByTimeId_TimeId(UUID id);
}
