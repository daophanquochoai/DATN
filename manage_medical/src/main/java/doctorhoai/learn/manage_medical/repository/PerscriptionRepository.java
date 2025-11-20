package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.Perscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerscriptionRepository extends JpaRepository<Perscriptions, UUID> {
    List<Perscriptions> getPerscriptionsByMealRelation_RelationsId(UUID id);
    List<Perscriptions> getPerscriptionsByUnitDosageId_UnitId(UUID id);
    List<Perscriptions> getPerscriptionsByDrugId_DrugId(UUID id);
}
