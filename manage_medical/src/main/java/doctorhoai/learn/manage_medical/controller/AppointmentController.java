package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.request.AppointmentCreate;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.AppointmentFilter;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftEmployeeDto;
import doctorhoai.learn.manage_medical.service.appointment.AppointmentService;
import doctorhoai.learn.manage_medical.service.follow_up_visit.FollowUpVisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final FollowUpVisitService followUpVisitService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createAppointment(
            @Valid @RequestBody AppointmentCreate appointmentCreate
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.createAppointment(appointmentCreate))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getAppointmentList(
            @RequestBody AppointmentFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointment(filter))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getAppointmentById(
        @PathVariable UUID id
    ) throws ExecutionException, InterruptedException {
           return ResponseEntity.ok(
                   ResponseObject.builder()
                           .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                           .data(appointmentService.getAppointmentById(id))
                           .build()
           );
    }

    @GetMapping("/check/{roomId}")
    public ResponseEntity<ResponseObject> checkAppointment(
            @PathVariable UUID roomId
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentById(roomId))
                        .build()
        );
    }

    @GetMapping("/check/booked/{serviceId}/{patientId}")
    public ResponseEntity<ResponseObject> checkAppointmentBooked(
            @RequestParam LocalDate date,
            @PathVariable UUID patientId,
            @PathVariable UUID serviceId
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.checkAppointmentWasBook(date, patientId, serviceId))
                        .build()
        );
    }

    @GetMapping("/count-by-shift-ids")
    public ResponseEntity<ResponseObject> getAppointmentCountByShiftIds(
            @RequestParam Set<UUID> shiftIds
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentCountByShiftIds(shiftIds))
                        .build()
        );
    }

    @GetMapping("/get-by-patient-service-and-shifts")
    public ResponseEntity<ResponseObject> getAppointmentByPatientAndDateAndServiceAndShift(
            @RequestParam UUID patientId,
            @RequestParam UUID serviceId,
            @RequestParam List<ShiftEmployeeDto> shiftIds
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentByPatientAndDateAndServiceAndEmployee(patientId, serviceId, shiftIds))
                        .build()
        );
    }

    @GetMapping("/follow_up_visits" )
    public ResponseEntity<ResponseObject> getFollowUpVisits(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(followUpVisitService.getFollowUpVisitStatistics(startDate, endDate))
                        .build()
        );
    }

    @GetMapping("/count-by-service")
    public ResponseEntity<ResponseObject> getAppointmentCountByServiceId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentCountByServiceId(startDate, endDate))
                        .build()
        );
    }

    @GetMapping("/count-by-day")
    public ResponseEntity<ResponseObject> getAppointmentCountByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentStatusCount(startDate, endDate))
                        .build()
        );
    }

    @GetMapping("/old-appointment")
    public ResponseEntity<ResponseObject> getOldAppointment(
            @RequestParam UUID employeeId,
            @RequestParam String icd10Id
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getOldAppointment(employeeId, icd10Id))
                        .build()
        );
    }

    @GetMapping("/shift-employee/{id}")
    public ResponseEntity<ResponseObject> getAppointmentByShiftId(
            @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointmentService.getAppointmentByShiftId(id))
                        .build()
        );
    }
}
