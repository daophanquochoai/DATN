package doctorhoai.learn.manage_account.service.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ServiceDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ServiceFilter;
import doctorhoai.learn.manage_account.exception.exception.ServiceNotFoundException;
import doctorhoai.learn.manage_account.feign.appointment.AppointmentFeign;
import doctorhoai.learn.manage_account.feign.dto.AppointmentDto;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.EmployeeServices;
import doctorhoai.learn.manage_account.model.Services;
import doctorhoai.learn.manage_account.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Service_ServiceImpl implements Service_Service{

    private final ServiceRepository serviceRepository;
    private final Mapper mapper;
    private final AppointmentFeign appointmentFeign;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        Services services = mapper.convertToServices(serviceDto);
        services = serviceRepository.save(services);
        return mapper.convertToServiceDto(services);
    }

    @Override
    public ServiceDto getServiceById(UUID serviceId, UUID patientId) throws IOException {
        Services services = serviceRepository.getServicesByServiceId(serviceId).orElseThrow(ServiceNotFoundException::new);
        ServiceDto serviceDto = mapper.convertToServiceDto(services);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<UUID> employeeIds = new ArrayList<>();
        if( patientId != null ){
            ResponseEntity<ResponseObject> response = appointmentFeign.checkAppointmentBooked(
                    LocalDate.now(),
                    patientId, serviceId
            );
            if( response.getStatusCode().is2xxSuccessful()){
                List<EmployeeDto> employDtos = objectMapper.convertValue(
                        response.getBody().getData(),
                        new TypeReference<List<EmployeeDto>>() {}
                );
                employeeIds = employDtos.stream().map(EmployeeDto::getEmployeeId).toList();
            }
        }
        for(EmployeeServices employees : services.getEmployeeServices()){
            if( !employeeIds.isEmpty() && employeeIds.contains(employees.getEmployees().getEmployeeId())){
                continue;
            }
            employeeDtos.add(mapper.convertToEmployeeDto(employees.getEmployees()));
        }
        serviceDto.setEmployeeDtos(employeeDtos);
        return serviceDto;
    }

    @Override
    public ServiceDto updateService(UUID serviceId, ServiceDto serviceDto) {
        Services services = serviceRepository.getServicesByServiceId(serviceId).orElseThrow(ServiceNotFoundException::new);
        services.setName(serviceDto.getName());
        services.setDescription(serviceDto.getDescription());
        services.setPrice(serviceDto.getPrice());
        services.setImage(serviceDto.getImage());
        services = serviceRepository.save(services);
        return mapper.convertToServiceDto(services);
    }

    @Override
    public void deleteService(UUID serviceId) {
        Services services = serviceRepository.getServicesByServiceId(serviceId).orElseThrow(ServiceNotFoundException::new);
        if( services.getEmployeeServices() != null && !services.getEmployeeServices().isEmpty()){
            throw new BadException("Cannot delete service that is associated with employees");
        }
        serviceRepository.delete(services);
    }

    @Override
    public PageObject getAllService(ServiceFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<Services> servicesPage = serviceRepository.getServicesByFilter(
                filter.getSearch(),
                filter.getStartPrice(),
                filter.getEndPrice(),
                filter.getServiceIds(),
                filter.getStartDate(),
                filter.getEndDate(),
                pageable
        );
        return PageObject.builder()
                .data(servicesPage.stream().map(mapper::convertToServiceDto).toList())
                .pageNo(filter.getPageNo())
                .totalPage(servicesPage.getTotalPages())
                .build();
    }

    @Override
    public List<ServiceDto> getServiceByIds(List<UUID> ids) {
        List<Services> services = serviceRepository.getServicesByIds(ids);
        return services.stream().map(mapper::convertToServiceDto).toList();
    }
}
