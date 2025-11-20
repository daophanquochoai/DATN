package doctorhoai.learn.manage_account.service.services;

import doctorhoai.learn.manage_account.dto.ServiceDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ServiceFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface Service_Service {
    ServiceDto createService(ServiceDto serviceDto);
    ServiceDto getServiceById(UUID serviceId, UUID patientId) throws IOException;
    ServiceDto updateService(UUID serviceId, ServiceDto serviceDto);
    void deleteService(UUID serviceId);
    PageObject getAllService(ServiceFilter filter);
    List<ServiceDto> getServiceByIds( List<UUID> ids);
}
