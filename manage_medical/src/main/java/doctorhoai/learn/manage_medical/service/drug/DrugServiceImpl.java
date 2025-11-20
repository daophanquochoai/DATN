package doctorhoai.learn.manage_medical.service.drug;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.DrugDto;
import doctorhoai.learn.manage_medical.dto.filter.DrugFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.exception.exception.DrugNotFoundException;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.Drug;
import doctorhoai.learn.manage_medical.model.Perscriptions;
import doctorhoai.learn.manage_medical.repository.DrugRepository;
import doctorhoai.learn.manage_medical.repository.PerscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;
    private final PerscriptionRepository perscriptionRepository;
    private final Mapper mapper;

    @Override
    public DrugDto createDrug(DrugDto drugDto) {

        Drug drug = mapper.convertToDrug(drugDto);
        drug = drugRepository.save(drug);
        return mapper.convertToDrugDto(drug);
    }

    @Override
    public DrugDto updateDrug(UUID id, DrugDto drugDto) {
        Drug drug = drugRepository.getDrugByDrugId(id).orElseThrow(DrugNotFoundException::new);
        drug.setName(drugDto.getName());
        drug.setGenericName(drugDto.getGenericName());
        drug.setDescription(drugDto.getDescription());
        drug.setPacking(drugDto.getPacking());
        drug.setSideEffects(drugDto.getSideEffects());
        drug.setContraindication(drugDto.getContraindication());
        drug.setAllergyInfo(drugDto.getAllergyInfo());
        drug = drugRepository.save(drug);
        return mapper.convertToDrugDto(drug);
    }

    @Override
    public DrugDto getDrugById(UUID id) {
        Drug drug = drugRepository.getDrugByDrugId(id).orElseThrow(DrugNotFoundException::new);
        return mapper.convertToDrugDto(drug);
    }

    @Override
    public PageObject getDrugPage(DrugFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        }
        Page<Drug> drugPage = drugRepository.getDrugByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                pageable
        );
        return PageObject.builder()
                .totalPage(drugPage.getTotalPages())
                .pageNo(filter.getPageNo())
                .data(drugPage.getContent().stream().map(mapper::convertToDrugDto))
                .build();
    }

    @Override
    public void deleteByDrug(UUID id) {
        List<Perscriptions> perscriptions = perscriptionRepository.getPerscriptionsByDrugId_DrugId(id);
        if(!perscriptions.isEmpty()){
            throw new BadException("Cannot delete meal relation because it is referenced by existing prescriptions.");
        }
        Drug drug = drugRepository.getDrugByDrugId(id).orElseThrow(DrugNotFoundException::new);
        drugRepository.delete(drug);
    }
}
