package doctorhoai.learn.indentity_service.feign.manage_account;


import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationFilter;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationsDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authSpecialization",
        path = "/specialization",
        fallbackFactory = SpecializationFeignFallback.class,
        configuration = FeignConfig.class
)
public interface SpecializationFeign {
    @PostMapping("/create")
    ResponseEntity<?> createSpecialization(
            @Valid @RequestBody SpecializationsDto dto
    );

    /**
     * get specialization by id
     * @param id - id of specialization
     * @return - specialization
     */
    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getSpecializationById(
            @PathVariable UUID id
    );

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getSpecializationList(
            @Valid @RequestBody SpecializationFilter filter
    );

    /**
     * update specialization by id
     * @param id - id specialization
     * @param dto - dto
     * @return - specialization after update
     */
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateSpecializationById(
            @PathVariable UUID id,
            @Valid @RequestBody SpecializationsDto dto
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteSpecializationById(
            @PathVariable UUID id
    );
}
