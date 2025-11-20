package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationFilter;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationsDto;
import doctorhoai.learn.indentity_service.feign.manage_account.SpecializationFeign;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationFeign specializationFeign;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> createSpecialization(
            @Valid @RequestBody SpecializationsDto dto
    ){
        return specializationFeign.createSpecialization(dto);
    }

    /**
     * get specialization by id
     * @param id - id of specialization
     * @return - specialization
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getSpecializationById(
            @PathVariable UUID id
    )
    {
        return specializationFeign.getSpecializationById(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getSpecializationList(
            @Valid @RequestBody SpecializationFilter filter,
            ServletRequest servletRequest)
    {
        return specializationFeign.getSpecializationList(filter);
    }

    /**
     * update specialization by id
     * @param id - id specialization
     * @param dto - dto
     * @return - specialization after update
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> updateSpecializationById(
            @PathVariable UUID id,
            @Valid @RequestBody SpecializationsDto dto
    )
    {
        return specializationFeign.updateSpecializationById(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> deleteSpecializationById(
            @PathVariable UUID id
    )
    {
        return specializationFeign.deleteSpecializationById(id);
    }

}
