package doctorhoai.learn.manage_medical.service.meal_relation;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.MealRelationDto;
import doctorhoai.learn.manage_medical.dto.filter.MealRelationFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.exception.exception.MealRelationNotFoundException;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.MealRelation;
import doctorhoai.learn.manage_medical.model.Perscriptions;
import doctorhoai.learn.manage_medical.repository.MealRelationRepository;
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
public class MealRelationServiceImpl implements MealRelationService{

    private final Mapper mapper;
    private final MealRelationRepository mealRelationRepository;
    private final PerscriptionRepository perscriptionRepository;

    @Override
    public MealRelationDto createMealRelation(MealRelationDto mealRelationDto) {
        MealRelation mealRelation = mapper.convertToMealRelation(mealRelationDto);
        mealRelation = mealRelationRepository.save(mealRelation);
        return mapper.convertToMealRelationDto(mealRelation);
    }

    @Override
    public MealRelationDto getMealRelationById(UUID id) {
        MealRelation mealRelation = mealRelationRepository.getMealRelationByRelationsId(id).orElseThrow(MealRelationNotFoundException::new);
        return mapper.convertToMealRelationDto(mealRelation);
    }

    @Override
    public MealRelationDto updateMealRelation(UUID id, MealRelationDto mealRelationDto) {
        MealRelation mealRelation = mealRelationRepository.getMealRelationByRelationsId(id).orElseThrow(MealRelationNotFoundException::new);
        mealRelation.setName(mealRelationDto.getName());
        mealRelation.setDescription(mealRelationDto.getDescription());
        mealRelation = mealRelationRepository.save(mealRelation);
        return mapper.convertToMealRelationDto(mealRelation);
    }

    @Override
    public PageObject getAllMealRelation(MealRelationFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<MealRelation> mealRelationPage = mealRelationRepository.getMealRelationByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                pageable
        );
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(mealRelationPage.getTotalPages())
                .data(mealRelationPage.getContent().stream().map(mapper::convertToMealRelationDto).toList())
                .build();
    }

    @Override
    public void deleteMealRelationById(UUID id) {
        List<Perscriptions> perscriptions = perscriptionRepository.getPerscriptionsByMealRelation_RelationsId(id);
        if(!perscriptions.isEmpty()) {
            throw new BadException("Cannot delete meal relation because it is referenced by existing prescriptions.");
        }
        MealRelation mealRelation = mealRelationRepository.getMealRelationByRelationsId(id).orElseThrow(MealRelationNotFoundException::new);
        mealRelationRepository.delete(mealRelation);
    }
}
