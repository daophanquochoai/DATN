package doctorhoai.learn.manage_medical.service.appointment_records;

import com.fasterxml.jackson.databind.ObjectMapper;
import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.AppointmentRecordDto;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.request.AppointmentRecordCreate;
import doctorhoai.learn.manage_medical.dto.request.PerscriptionCreate;
import doctorhoai.learn.manage_medical.exception.UnitNotFoundException;
import doctorhoai.learn.manage_medical.exception.exception.*;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftDto;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftEmployeeDto;
import doctorhoai.learn.manage_medical.feign.feign.shift.ShiftFeign;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.*;
import doctorhoai.learn.manage_medical.model.appointment.Appointment;
import doctorhoai.learn.manage_medical.model.appointment.AppointmentStatus;
import doctorhoai.learn.manage_medical.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentRecordServiceImpl implements AppointRecordService{

    private final AppointmentRecordRepository appointmentRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final PerscriptionRepository perscriptionRepository;
    private final DrugRepository drugRepository;
    private final UnitsRepository unitsRepository;
    private final MealRelationRepository mealRelationRepository;
    private final DosageTimeRepository dosageTimeRepository;
    private final FollowUpVisitRepository followUpVisitRepository;
    private final ICD10Repository icd10Repository;
    private final Mapper mapper;
    private final ShiftFeign shiftFeign;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public AppointmentRecordDto createAppointmentRecord(AppointmentRecordCreate create) {

        Appointment appointment = appointmentRepository.getAppointmentByAppointmentId(create.getAppointment()).orElseThrow(AppointmentNotFoundException::new);
        appointmentRecordRepository.getAppointmentRecordByAppointment_AppointmentId(create.getAppointment()).ifPresent(
                record -> {
                    throw new BadException("Kết quả khám đã tồn tại!");
                }
        );

        ResponseEntity<ResponseObject> response = shiftFeign.getShiftEmployeeById(appointment.getShiftId());
        ResponseObject responseObject = response.getBody();
        if( responseObject == null || responseObject.getData() == null ){
            throw new BadException("Không lấy được thông tin ca làm việc!");
        }
        ShiftEmployeeDto shiftEmployeeDto = objectMapper.convertValue(responseObject.getData(), ShiftEmployeeDto.class);

        if( create.getFollow() != null){
            FollowUpVisit followUpVisit = followUpVisitRepository.getFollowUpVisitByFollowUpId(create.getFollow()).orElseThrow(FollowUpVisitNotFoundException::new);
            if( followUpVisit.getAppointment() != null ){
                throw new BadException("Cuộc hẹn tái khám đã được sử dụng!");
            }
            if (followUpVisit.getFollowUpDate().isAfter(shiftEmployeeDto.getDate())) {
                throw new BadException("Ngày tái khám phải trước\n ngày khám ban đầu!");
            }
            if( !followUpVisit.getAppointmentRecord().getAppointment().getServiceId().equals(appointment.getServiceId())){
                throw new BadException("Dịch vụ của cuộc hẹn tái khám không khớp!");
            }
            followUpVisit.setAppointment(appointment);
            followUpVisitRepository.save(followUpVisit);
        }
        if( appointment.getStatus() == AppointmentStatus.COMPLETE){
            throw new BadException("Cuộc hẹn đã hoàn thành!");
        }

        ICD10Codes icd10Codes = icd10Repository.getICD10CodesByCode(create.getIcd10()).orElseThrow(ICD10NotFoundException::new);

        AppointmentRecord appointmentRecord = AppointmentRecord.builder()
                .appointment(appointment)
                .height(create.getHeight())
                .weight(create.getWeight())
                .bloodPressure(create.getBloodPressure())
                .temperature(create.getTemperature())
                .heartRate(create.getHeartRate())
                .spo2(create.getSpo2())
                .symptoms(create.getSymptoms())
                .initialDiagnosis(create.getInitialDiagnosis())
                .finalDiagnosis(create.getFinalDiagnosis())
                .notes(create.getNotes())
                .icd10Codes(icd10Codes)
                .build();

        if( create.getFollowUpVisit() != null ){
            FollowUpVisit follow = FollowUpVisit.builder()
                    .appointmentRecord(appointmentRecord)
                    .followUpDate(create.getFollowUpVisit().getFollowUpDate())
                    .instruction(create.getFollowUpVisit().getInstruction())
                    .build();

            if( follow.getFollowUpDate().isBefore(shiftEmployeeDto.getDate())){
                throw new BadException("Ngày tái khám phải sau ngày khám ban đầu!");
            }
            appointmentRecord.setFollowUpVisit(follow);
        }

        Set<UUID> drugIds = create.getPerscriptionCreates().stream().map(PerscriptionCreate::getDrugId).filter(Objects::nonNull).collect(Collectors.toSet());
        Set<UUID> unitIds = create.getPerscriptionCreates().stream().map(PerscriptionCreate::getUnitDosageId).collect(Collectors.toSet());
        Set<UUID> mealIds = create.getPerscriptionCreates().stream().map(PerscriptionCreate::getMealRelation).collect(Collectors.toSet());
        Set<UUID> dosageIds = create.getPerscriptionCreates()
                .stream()
                .map(PerscriptionCreate::getDosageTimeDtos)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        Set<Drug> drugSet = drugRepository.getDrugByDrugIdIn(drugIds);
        Set<Units> unitSet = unitsRepository.getUnitsByUnitIdIn(unitIds);
        Set<MealRelation> mealRelations = mealRelationRepository.getMealRelationByRelationsIdIn(mealIds);
        Set<DosageTimes> dosageTimes = dosageTimeRepository.getDosageTimesByTimeIdIn(dosageIds);

        List<Perscriptions> perscriptions = create.getPerscriptionCreates().stream().map( i -> {
            Perscriptions per = Perscriptions.builder()
                    .drugId(getDrugInSet(drugSet, i.getDrugId()))
                    .recordId(appointmentRecord)
                    .customDrugName(i.getCustomDrugName())
                    .dosage(i.getDosage())
                    .frequency(i.getFrequency())
                    .duration(i.getDuration())
                    .unitDosageId(getUnitInSet(unitSet, i.getUnitDosageId()))
                    .instructions(i.getInstructions())
                    .mealRelation(getMealRelationInSet(mealRelations,i.getMealRelation()))
                    .build();
            List<PerscriptionTime> perscriptionTimes = i.getDosageTimeDtos().stream().map( d -> {
                return PerscriptionTime.builder()
                        .perscriptionId(per)
                        .timeId(getDosageTimeInSet(dosageTimes,d))
                        .build();
            }).toList();
            per.setPerscriptionTimes(perscriptionTimes);
            return per;
        }).toList();

        appointmentRecord.setPerscriptions(perscriptions);
        AppointmentRecord appointmentRecordSaved = appointmentRecordRepository.save(appointmentRecord);
        appointment.setStatus(AppointmentStatus.COMPLETE);
        appointmentRepository.save(appointment);
        return mapper.convertToAppointmentRecord(appointmentRecordSaved);
    }

    @Override
    public AppointmentRecordDto getAppointmentById(UUID id) {
        AppointmentRecord appointmentRecord = appointmentRecordRepository.getAppointmentRecordByAppointment_AppointmentId(id)
                .orElseThrow(AppointmentRecordNotFoundException::new);
        return mapper.convertToAppointmentRecord(appointmentRecord);
    }


    public Drug getDrugInSet(Set<Drug> drugSet, UUID id){
        for( Drug drug : drugSet){
            if( drug.getDrugId().equals(id)){
                return drug;
            }
        }
        return null;
    }

    public Units getUnitInSet(Set<Units> unitSet, UUID id){
        for( Units units : unitSet ){
            if( units.getUnitId().equals(id)){
                return units;
            }
        }
        throw new UnitNotFoundException();
    }

    public MealRelation getMealRelationInSet(Set<MealRelation> mealRelationSet, UUID id){
        for( MealRelation mealRelation : mealRelationSet){
            if( mealRelation.getRelationsId().equals(id)){
                return mealRelation;
            }
        }
        throw new MealRelationNotFoundException();
    }

    public DosageTimes getDosageTimeInSet(Set<DosageTimes> dosageTimesSet, UUID id){
        for ( DosageTimes dosageTimes : dosageTimesSet){
            if( dosageTimes.getTimeId().equals(id)){
                return dosageTimes;
            }
        }
        throw new DosageTimeNotFoundException();
    }
}
