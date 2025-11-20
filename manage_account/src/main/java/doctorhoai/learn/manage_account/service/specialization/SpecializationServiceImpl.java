package doctorhoai.learn.manage_account.service.specialization;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.SpecializationsDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.SpecializationFilter;
import doctorhoai.learn.manage_account.exception.exception.SpecializationNotFoundException;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.Employees;
import doctorhoai.learn.manage_account.model.Specializations;
import doctorhoai.learn.manage_account.repository.EmployeesRepository;
import doctorhoai.learn.manage_account.repository.SpecializationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationsRepository specializationsRepository;
    private final EmployeesRepository employeesRepository;
    private final Mapper mapper;

    /**
     * create specialization
     * @param specializationsDto - dto class
     * @return - class
     */
    @Override
    @Transactional
    public SpecializationsDto createSpecializations(SpecializationsDto specializationsDto) {
        // map to class
        Specializations specializations = mapper.convertToSpecializations(specializationsDto);
        return mapper.convertToSpecializationsDto(specializationsRepository.save(specializations));
    }

    /**
     * get specialization by id
     * @param specializationId - id specialization
     * @return - specialization
     */
    @Override
    public SpecializationsDto getSpecializations(UUID specializationId) {
        // get specialization
        Specializations specializations = specializationsRepository.findBySpecializationId(specializationId).orElseThrow(SpecializationNotFoundException::new);
        SpecializationsDto specializationsDto = mapper.convertToSpecializationsDto(specializations);
        List<EmployeeDto> employees = new ArrayList<>();
        for( Employees e : specializations.getEmployees()){
            employees.add(mapper.convertToEmployeeDto(e));
        }
        specializationsDto.setEmployeeDtos(employees);
        return specializationsDto;
    }

    /**
     * delete specialization
     * @param specializationId - id specialization
     */
    @Override
    public void deleteSpecializations(UUID specializationId) {
        // check to can remove
        List<Employees> employeesList = employeesRepository.checkRemoveSpecialization(specializationId);
        if(!employeesList.isEmpty()){
            throw new BadException("Please delete the related data first.");
        }
        specializationsRepository.deleteById(specializationId);
    }

    /**
     * update specialization
     * @param id - id specialization
     * @param specializationsDto - dto before update
     * @return - dto after update
     */
    @Override
    public SpecializationsDto updateSpecializations(UUID id, SpecializationsDto specializationsDto) {
        // check exist
        Specializations specializations = specializationsRepository.findBySpecializationId(id).orElseThrow(SpecializationNotFoundException::new);
        specializations.setName(specializationsDto.getName());
        specializations.setDescription(specializationsDto.getDescription());
        Specializations specializationsUpdated = specializationsRepository.save(specializations);
        return mapper.convertToSpecializationsDto(specializationsUpdated);
    }

    @Override
    public PageObject getSpecializations(SpecializationFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        }
        Page<Specializations> specializationsPage = specializationsRepository.findSpecializationsByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                pageable
        );

        PageObject pageObject = PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(specializationsPage.getTotalPages())
                .data(specializationsPage.getContent().stream().map(mapper::convertToSpecializationsDto).toList())
                .build();
        return pageObject;
    }
}
