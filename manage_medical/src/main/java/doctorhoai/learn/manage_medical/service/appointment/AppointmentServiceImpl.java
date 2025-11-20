package doctorhoai.learn.manage_medical.service.appointment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.AppointmentRecordDto;
import doctorhoai.learn.manage_medical.dto.request.AppointmentCreate;
import doctorhoai.learn.manage_medical.dto.AppointmentDto;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.AppointmentFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.exception.exception.*;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.employee.EmployeeDto;
import doctorhoai.learn.manage_medical.feign.dto.employee.RoomDto;
import doctorhoai.learn.manage_medical.feign.dto.patient.PatientsDto;
import doctorhoai.learn.manage_medical.feign.dto.patient.PatientsFilter;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceDto;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceFilter;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftEmployeeDto;
import doctorhoai.learn.manage_medical.feign.feign.patient.PatientFeign;
import doctorhoai.learn.manage_medical.feign.feign.room.RoomFeign;
import doctorhoai.learn.manage_medical.feign.feign.service.ServiceFeign;
import doctorhoai.learn.manage_medical.feign.feign.shift.ShiftFeign;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.AppointmentRecord;
import doctorhoai.learn.manage_medical.model.appointment.Appointment;
import doctorhoai.learn.manage_medical.model.appointment.AppointmentStatus;
import doctorhoai.learn.manage_medical.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private final RoomFeign roomFeign;
    private final PatientFeign patientFeign;
    private final ServiceFeign serviceFeign;
    private final AppointmentRepository appointmentRepository;
    private final ShiftFeign shiftFeign;
    private final Mapper mapper;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getShiftById(UUID id){
            return CompletableFuture.supplyAsync(() -> shiftFeign.getShiftEmployeeById(id));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getPatientById(UUID id, AccountStatus status) {
        return CompletableFuture.supplyAsync(() -> patientFeign.getPatientsById(id,status));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getServiceById(UUID id) {
        return CompletableFuture.supplyAsync(() -> serviceFeign.getServiceById(id, null));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getShiftByIds(List<UUID> ids){
        return CompletableFuture.supplyAsync(() -> shiftFeign.getShiftEmployeeByIds(ids));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getPatientIds(List<UUID> ids){
        return CompletableFuture.supplyAsync(() -> patientFeign.getPatientByIds(PatientsFilter.builder().ids(ids).build()));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getServiceByIds(List<UUID> ids){
        return CompletableFuture.supplyAsync(() -> serviceFeign.getServiceById(ServiceFilter.builder().serviceIds(ids).build()));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getRoomById(UUID id){
        return CompletableFuture.supplyAsync(() -> roomFeign.getRoomById(id));
    }

    @Async
    public CompletableFuture<ResponseEntity<ResponseObject>> getRoomByIds(List<UUID> ids){
        return CompletableFuture.supplyAsync(() -> roomFeign.getRoomByIds(ids));
    }

    @Override
    @Transactional
    public AppointmentDto createAppointment(AppointmentCreate dto) {
        CompletableFuture<ResponseEntity<ResponseObject>> shift = getShiftById(dto.getShiftId());
        CompletableFuture<ResponseEntity<ResponseObject>> patient = getPatientById(dto.getPatientId(), AccountStatus.ACTIVE);
        CompletableFuture<ResponseEntity<ResponseObject>> service = getServiceById(dto.getServiceId());
        try{
            CompletableFuture.allOf(shift, patient, service).join();

            ResponseObject shiftObject = shift.get().getBody();
            ShiftEmployeeDto shiftDto = objectMapper.convertValue(shiftObject.getData(), ShiftEmployeeDto.class);
            List<Appointment> appointments = appointmentRepository.getAppointmentByShiftId(shiftDto.getId(), List.of( AppointmentStatus.PAYMENT, AppointmentStatus.COMPLETE));
            if( shiftDto.getPatientSlot() <= appointments.size()){
                throw new BadException("Đã bị đặt hết rồi?");
            }

            ResponseEntity<ResponseObject> response = shiftFeign.getShiftEmployeeByEmployeeIds(List.of(shiftDto.getEmployeeDto().getEmployeeId()), shiftDto.getDate());
            ResponseObject shiftEmployeeObject = response.getBody();
            List<ShiftEmployeeDto> shiftEmployeeDtos = objectMapper.convertValue(shiftEmployeeObject.getData(),
                    new TypeReference<List<ShiftEmployeeDto>>() {}
            );
            List<UUID> ids = shiftEmployeeDtos.stream().map(ShiftEmployeeDto::getId).toList();

            List<Appointment> appointmentBooked = appointmentRepository.getAppointmentBooked(
                    dto.getPatientId(),
                    dto.getServiceId(),
                    ids,
                    List.of(AppointmentStatus.PAYMENT, AppointmentStatus.COMPLETE)
            );
            if(!appointmentBooked.isEmpty()){
                throw new BadException("Bạn đã đặt dịch vụ này trong ca làm việc của nhân viên này rồi?");
            }

            Appointment appointment = Appointment.builder()
                    .patientId(dto.getPatientId())
                    .shiftId(dto.getShiftId())
                    .roomId(shiftDto.getEmployeeDto().getRoomDto().getRoomId())
                    .serviceId(dto.getServiceId())
                    .price(dto.getPrice())
                    .status(AppointmentStatus.CREATE)
                    .fullname(dto.getFullname())
                    .dob(dto.getDob())
                    .gender(dto.getGender())
                    .address(dto.getAddress())
                    .insuranceCode(dto.getInsuranceCode())
                    .emergencyContact(dto.getEmergencyContact())
                    .citizenId(dto.getCitizenId())
                    .job(dto.getJob())
                    .phoneNumber(dto.getPhoneNumber())
                    .build();
            Appointment appointmentSaved = appointmentRepository.save(appointment);

            ResponseObject patientObject = patient.get().getBody();
            PatientsDto patientsDto = objectMapper.convertValue(patientObject.getData(), PatientsDto.class);
            ResponseObject serviceObject = service.get().getBody();
            ServiceDto serviceDto = objectMapper.convertValue(serviceObject.getData(), ServiceDto.class);
            return AppointmentDto.builder()
                    .appointmentId(appointmentSaved.getAppointmentId())
                    .patientId(patientsDto)
                    .shiftId(shiftDto)
                    .serviceId(serviceDto)
                    .roomDto(shiftDto.getEmployeeDto().getRoomDto())
                    .price(appointmentSaved.getPrice())
                    .fullname(dto.getFullname())
                    .dob(dto.getDob())
                    .gender(dto.getGender())
                    .address(dto.getAddress())
                    .insuranceCode(dto.getInsuranceCode())
                    .emergencyContact(dto.getEmergencyContact())
                    .citizenId(dto.getCitizenId())
                    .job(dto.getJob())
                    .phoneNumber(dto.getPhoneNumber())
                    .build();
        }catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
        }
    }

    @Override
    public PageObject getAppointment(AppointmentFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        }

        ResponseEntity<ResponseObject> response = shiftFeign.getShiftEmployeeByEmployeeIds(filter.getEmployeeId(), LocalDate.now());
        ResponseObject shiftEmployeeObject = response.getBody();
        List<ShiftEmployeeDto> shiftEmployeeDtos = objectMapper.convertValue(shiftEmployeeObject.getData(),
                new TypeReference<List<ShiftEmployeeDto>>() {}
        );
        List<UUID> shiftIds = shiftEmployeeDtos.stream().map(ShiftEmployeeDto::getId).toList();

        if( filter.getSearch() != null && filter.getEmployeeId() != null ){
            List<Appointment> appointments = appointmentRepository.getAppointmentsByShiftIds(shiftIds);
            List<UUID> patientIds = appointments.stream().map(Appointment::getPatientId).toList();
            ResponseEntity<ResponseObject> patientResponse = patientFeign.getAllPatients(filter.getSearch(),patientIds);
            ResponseObject patientObject = patientResponse.getBody();
            List<PatientsDto> patientsDtos = objectMapper.convertValue(patientObject.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PatientsDto.class));
            List<UUID> filteredPatientIds = patientsDtos.stream().map(PatientsDto::getPatientId).toList();
            filter.setPatientId(Stream.concat(
                    filter.getPatientId() != null ? filter.getPatientId().stream() : Stream.empty(),
                    filteredPatientIds.stream()
            ).distinct().toList());
        }

        Page<Appointment> appointmentPage = appointmentRepository.getAppointmentByFilter(
                shiftIds.isEmpty() ? null : shiftIds,
                filter.getPatientId(),
                filter.getStatuses(),
                pageable
        );
        List<Appointment> appointments = appointmentPage.getContent();
        List<UUID> patientIds = appointments.stream().map(Appointment::getPatientId).toList();
        List<UUID> serviceIds = appointments.stream().map(Appointment::getServiceId).toList();
        List<UUID> roomIds = appointments.stream().map(Appointment::getRoomId).toList();
        List<UUID> shiftReturnIds = appointments.stream().map(Appointment::getShiftId).toList();
        try{
            CompletableFuture<ResponseEntity<ResponseObject>> roomList = getRoomByIds(roomIds);
            CompletableFuture<ResponseEntity<ResponseObject>> patientList = getPatientIds(patientIds);
            CompletableFuture<ResponseEntity<ResponseObject>> serviceList = getServiceByIds(serviceIds);
            CompletableFuture<ResponseEntity<ResponseObject>> shiftReturnList = getShiftByIds(shiftReturnIds);
            CompletableFuture.allOf( patientList, serviceList, roomList, shiftReturnList).join();
            ResponseObject patientObject = patientList.get().getBody();
            ResponseObject serviceObject = serviceList.get().getBody();
            ResponseObject roomObject = roomList.get().getBody();
            ResponseObject shiftObject = shiftReturnList.get().getBody();
            List<PatientsDto> patientDtos = objectMapper.convertValue(patientObject.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, PatientsDto.class));
            List<ServiceDto> serviceDtos = objectMapper.convertValue(serviceObject.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ServiceDto.class));
            List<RoomDto> roomDtos = objectMapper.convertValue(roomObject.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, RoomDto.class));
            List<ShiftEmployeeDto> shiftEmployeeDtosReturn = objectMapper.convertValue(shiftObject.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ShiftEmployeeDto.class));
            List<AppointmentDto> appointmentDtos = appointments.stream().map( (i) -> {
                ShiftEmployeeDto temp = getShiftEmployeeById(shiftEmployeeDtosReturn, i.getShiftId());
                RoomDto roomTemp = getRoomById(roomDtos, i.getRoomId());
              return AppointmentDto.builder()
                      .appointmentId(i.getAppointmentId())
                      .patientId(getPatientById(patientDtos,i.getPatientId()))
                      .shiftId(temp)
                      .roomDto(roomTemp)
                      .serviceId(getServiceById(serviceDtos,i.getServiceId()))
                      .payments(mapper.convertToPaymentDto(i.getPayments()))
                      .price(i.getPrice())
                      .status(i.getStatus())
                      .fullname(i.getFullname())
                      .dob(i.getDob())
                      .gender(i.getGender())
                      .address(i.getAddress())
                      .insuranceCode(i.getInsuranceCode())
                      .emergencyContact(i.getEmergencyContact())
                      .citizenId(i.getCitizenId())
                      .job(i.getJob())
                      .phoneNumber(i.getPhoneNumber())
                      .transactionCode(i.getTransactionCode())
                      .build();
            }).toList();
            return PageObject.builder()
                    .pageNo(filter.getPageNo())
                    .totalPage(appointmentPage.getTotalPages())
                    .data(appointmentDtos)
                    .build();
        }
        catch (Exception e){
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
        }
    }

    @Override
    @Transactional
    public AppointmentDto getAppointmentById(UUID id) throws ExecutionException, InterruptedException {
        Appointment appointment = appointmentRepository.getAppointmentByAppointmentId(id)
                .orElseThrow(AppointmentNotFoundException::new);

            CompletableFuture<ResponseEntity<ResponseObject>> room = getRoomById(appointment.getRoomId());
            CompletableFuture<ResponseEntity<ResponseObject>> shift = getShiftById(appointment.getShiftId());
            CompletableFuture<ResponseEntity<ResponseObject>> patient = getPatientById(appointment.getPatientId(), AccountStatus.ACTIVE);
            CompletableFuture<ResponseEntity<ResponseObject>> service = getServiceById(appointment.getServiceId());
            CompletableFuture.allOf(shift, patient, service, room).join();
            ResponseObject shiftObject = shift.get().getBody();
            ResponseObject patientObject = patient.get().getBody();
            ResponseObject serviceObject = service.get().getBody();
            ResponseObject responseObject = room.get().getBody();
            ShiftEmployeeDto shiftDto = objectMapper.convertValue(shiftObject.getData(), ShiftEmployeeDto.class);
            PatientsDto patientsDto = objectMapper.convertValue(patientObject.getData(), PatientsDto.class);
            ServiceDto serviceDto = objectMapper.convertValue(serviceObject.getData(), ServiceDto.class);
            RoomDto roomDto = objectMapper.convertValue(responseObject.getData(), RoomDto.class);
            return AppointmentDto.builder()
                    .appointmentId(appointment.getAppointmentId())
                    .patientId(patientsDto)
                    .shiftId(shiftDto)
                    .roomDto(roomDto)
                    .serviceId(serviceDto)
                    .status(appointment.getStatus())
                    .payments(mapper.convertToPaymentDto(appointment.getPayments()))
                    .price(appointment.getPrice())
                    .transactionCode(appointment.getTransactionCode())
                    .fullname(appointment.getFullname())
                    .dob(appointment.getDob())
                    .gender(appointment.getGender())
                    .address(appointment.getAddress())
                    .insuranceCode(appointment.getInsuranceCode())
                    .emergencyContact(appointment.getEmergencyContact())
                    .citizenId(appointment.getCitizenId())
                    .job(appointment.getJob())
                    .phoneNumber(appointment.getPhoneNumber())
                    .build();
    }
    @Override
    public void paymentAppointment(UUID appointmentId, String transactionNo) {
        Appointment appointment = appointmentRepository.getAppointmentByAppointmentId(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        appointment.setStatus(AppointmentStatus.PAYMENT);
        appointment.setTransactionCode(transactionNo);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<EmployeeDto> checkAppointmentWasBook(LocalDate date, UUID serviceId, UUID patientId) {
       try{
           List<Appointment> appointments = appointmentRepository.getAppointmentWasBook(patientId,serviceId,List.of(AppointmentStatus.PAYMENT,AppointmentStatus.COMPLETE));
           List<UUID> shiftIds = appointments.stream().map(Appointment::getShiftId).toList();
           CompletableFuture<ResponseEntity<ResponseObject>> shiftList = getShiftByIds(shiftIds);
           CompletableFuture.allOf(shiftList).join();
           ResponseObject shiftObject = shiftList.get().getBody();
           List<ShiftEmployeeDto> shiftEmployeeDtos = objectMapper.convertValue(shiftObject.getData(),
                   objectMapper.getTypeFactory().constructCollectionType(List.class, ShiftEmployeeDto.class));
           return shiftEmployeeDtos.stream().filter(i-> i.getDate().equals(date)).map(ShiftEmployeeDto::getEmployeeDto).toList();
       } catch (Exception e) {
           Thread.currentThread().interrupt();
           throw new RuntimeException("Error creating appointment: " + e.getMessage());
       }
    }

    @Override
    public Map<String, Long> getAppointmentCountByShiftIds(Set<UUID> shiftIds) {
        List<Object[]> counterObject =  appointmentRepository.countAppointmentByShiftIdIn(shiftIds, List.of(AppointmentStatus.COMPLETE, AppointmentStatus.PAYMENT));
        Map<UUID, Long> mapCounter =  counterObject.stream().collect(Collectors.toMap(
                row -> (UUID) row[0],
                row -> (Long) row[1]
        ));
        return shiftIds.stream().map( i -> {
            return Map.entry(i.toString(), mapCounter.getOrDefault(i, 0L));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<AppointmentDto> getAppointmentByPatientAndDateAndServiceAndEmployee(UUID patientId, UUID serviceId, List<ShiftEmployeeDto> shiftIds) {
        List<UUID> shifts = shiftIds.stream().map(ShiftEmployeeDto::getId).toList();
        List<Appointment> appointments = appointmentRepository.getAppointmentBooked(
                patientId,
                serviceId,
                shifts,
                List.of(AppointmentStatus.PAYMENT, AppointmentStatus.COMPLETE)
        );
        if(appointments.isEmpty()){
            return null;
        }
        return appointments.stream().map( i -> {
            ShiftEmployeeDto temp = getShiftEmployeeById(shiftIds,i.getShiftId());
            return AppointmentDto.builder()
                    .appointmentId(i.getAppointmentId())
                    .patientId(PatientsDto.builder().patientId(i.getPatientId()).build())
                    .shiftId(temp)
                    .serviceId(ServiceDto.builder().serviceId(i.getServiceId()).build())
                    .price(i.getPrice())
                    .status(i.getStatus())
                    .fullname(i.getFullname())
                    .dob(i.getDob())
                    .build();
        }).toList();
    }

    @Override
    public Map<UUID, Map<String, Object>> getAppointmentCountByServiceId(LocalDate startDate, LocalDate endDate) {
        List<Appointment> appointments = appointmentRepository.getAppointmentByServiceIdAndStatusInAndAppointmentDateBetween(
                startDate,
                endDate,
                List.of(AppointmentStatus.PAYMENT, AppointmentStatus.COMPLETE)
        );
        Map<UUID, Integer> countMap = new HashMap<>();
        List<UUID>  serviceIds = appointments.stream().map(Appointment::getServiceId).toList();
        ResponseEntity<ResponseObject> serviceDtos = serviceFeign.getServiceById(ServiceFilter.builder().serviceIds(serviceIds).build());
        ResponseObject serviceObject = serviceDtos.getBody();
        List<ServiceDto> serviceDtoList = objectMapper.convertValue(serviceObject.getData(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ServiceDto.class));
        for( Appointment appointment : appointments){
            countMap.put(appointment.getServiceId(),
                    countMap.getOrDefault(appointment.getServiceId(), 0) + 1);
        }
        Map<UUID, Map<String, Object>> result = new HashMap<>();
        for( UUID serviceId : countMap.keySet()){
            ServiceDto serviceDto = getServiceById(serviceDtoList, serviceId);
            result.put(serviceId,
                    Map.of(
                            "service", serviceDto,
                            "count", countMap.get(serviceId)
                    )
            );
        }
        return result;

    }

    @Override
    public Map<String, Integer> getAppointmentStatusCount(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = appointmentRepository.countAppointmentsByDate(startDate, endDate, List.of(AppointmentStatus.PAYMENT, AppointmentStatus.COMPLETE));

        Map<String, Integer> dateCount = new LinkedHashMap<>();
        for (Object[] result : results) {
            LocalDate date = (LocalDate) result[0];
            Long count = (Long) result[1];
            dateCount.put(date.toString(), count.intValue());
        }

        return dateCount;
    }

    @Override
    public AppointmentRecordDto getOldAppointment(UUID employeeId, String icd10Id) throws ExecutionException, InterruptedException {

        ResponseEntity<ResponseObject> shiftResponse = shiftFeign.getShiftEmployeeByEmployeeId(employeeId);
        ResponseObject shiftInMonth = shiftResponse.getBody();
        List<ShiftEmployeeDto> shiftEmployeeDtos = objectMapper.convertValue(shiftInMonth.getData(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ShiftEmployeeDto.class));
        List<UUID> shiftIds = shiftEmployeeDtos.stream().map(ShiftEmployeeDto::getId).toList();

        AppointmentRecord appointment = appointmentRepository.getOldAppointment(shiftIds, icd10Id)
                .orElse(null);
        if (appointment == null) return null;
        return mapper.convertToAppointmentRecord(appointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentByShiftId(UUID shiftId) {
        List<Appointment> appointments = appointmentRepository.getAppointmentByShiftId(shiftId);
        return appointments.stream().map( i -> {
            return AppointmentDto.builder()
                    .appointmentId(i.getAppointmentId())
                    .status(i.getStatus())
                    .build();
        }).toList();
    }

    public PatientsDto getPatientById(List<PatientsDto> patientsDtos, UUID id){
        for( PatientsDto patientsDto : patientsDtos){
            if( patientsDto.getPatientId().equals(id)){
                return patientsDto;
            }
        }
        throw new PatientsNotFoundException();
    }

    public ShiftEmployeeDto getShiftEmployeeById(List<ShiftEmployeeDto> shiftEmployeeDtos, UUID id){
        for(ShiftEmployeeDto shiftEmployeeDto : shiftEmployeeDtos){
            if( shiftEmployeeDto.getId().equals(id)){
                return shiftEmployeeDto;
            }
        }
        throw new BadException("Ca không tìm thấy?");
    }

    public ServiceDto getServiceById(List<ServiceDto> serviceDtos, UUID id){
        for(ServiceDto serviceDto : serviceDtos){
            if(serviceDto.getServiceId().equals(id)){
                return serviceDto;
            }
        }
        throw new ServiceNotFoundException();
    }

    public RoomDto getRoomById(List<RoomDto> roomDtos, UUID id){
        for ( RoomDto roomDto : roomDtos){
            if( roomDto.getRoomId().equals(id)){
                return roomDto;
            }
        }
        throw new BadException("Phòng không thể tìm thấy?");
    }
}
