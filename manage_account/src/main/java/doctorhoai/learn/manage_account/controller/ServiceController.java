package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ServiceDto;
import doctorhoai.learn.manage_account.dto.filter.ServiceFilter;
import doctorhoai.learn.manage_account.service.services.Service_Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
public class ServiceController {

    private final Service_Service service;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createService(
            @Valid @RequestBody ServiceDto serviceDto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(service.createService(serviceDto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getServiceById(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) UUID patientId
            ) throws IOException {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(service.getServiceById(id, patientId))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getAllService(
            @RequestBody ServiceFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(service.getAllService(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateService(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceDto serviceDto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(service.updateService(id, serviceDto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteService(
            @PathVariable("id") UUID id
            ) {
        service.deleteService(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PostMapping("/list/id")
    public ResponseEntity<ResponseObject> getServiceById(
            @RequestBody ServiceFilter filter
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(service.getServiceByIds(filter.getServiceIds()))
                        .build()
        );
    }
}
