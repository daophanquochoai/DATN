package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.SpecializationsDto;
import doctorhoai.learn.manage_account.dto.filter.SpecializationFilter;
import doctorhoai.learn.manage_account.service.specialization.SpecializationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    /**
     * create specialization
     * @param dto - dto
     * @return - specialization
     */
    @PostMapping("/create")
    public ResponseEntity<?> createSpecialization(
            @Valid @RequestBody SpecializationsDto dto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.SAVE_DATA_SUCCESSFUL.getMessage())
                        .data(specializationService.createSpecializations(dto))
                        .build()
        );
    }

    /**
     * get specialization by id
     * @param id - id of specialization
     * @return - specialization
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getSpecializationById(
            @PathVariable UUID id
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(specializationService.getSpecializations(id))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getSpecializationList(
            @Valid @RequestBody SpecializationFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(specializationService.getSpecializations(filter))
                        .build()
        );
    }

    /**
     * update specialization by id
     * @param id - id specialization
     * @param dto - dto
     * @return - specialization after update
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateSpecializationById(
            @PathVariable UUID id,
            @Valid @RequestBody SpecializationsDto dto
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(specializationService.updateSpecializations(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteSpecializationById(
            @PathVariable UUID id
    )
    {
        specializationService.deleteSpecializations(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

}
