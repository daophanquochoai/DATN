package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.ServiceDto;
import doctorhoai.learn.indentity_service.feign.dto.ServiceFilter;
import doctorhoai.learn.indentity_service.feign.manage_account.ServiceFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceFeign serviceFeign;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> createService(
            @Valid @RequestBody ServiceDto serviceDto
    )
    {
        return serviceFeign.createService(serviceDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getServiceById(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) UUID patientId
    )
    {
        return serviceFeign.getServiceById(id, patientId);
    }

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getAllService(
            @RequestBody ServiceFilter filter
    )
    {
        return serviceFeign.getAllService(filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> updateService(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceDto serviceDto
    )
    {
        return serviceFeign.updateService(id, serviceDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> deleteService(
            @PathVariable("id") UUID id
    )
    {
        return serviceFeign.deleteService(id);
    }

    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getServiceById(
            @RequestBody ServiceFilter filter
    )
    {
        return serviceFeign.getServiceById(filter);
    }
}
