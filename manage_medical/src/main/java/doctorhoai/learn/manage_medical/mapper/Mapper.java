package doctorhoai.learn.manage_medical.mapper;

import doctorhoai.learn.manage_medical.dto.*;
import doctorhoai.learn.manage_medical.model.*;
import org.springframework.data.jpa.domain.support.AuditingBeanFactoryPostProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper {

    public DosageTimes convertToDosageTimes(DosageTimeDto dto) {
        return DosageTimes.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public DosageTimeDto convertToDosageTimeDto(DosageTimes entity) {
        return DosageTimeDto.builder()
                .timeId(entity.getTimeId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public MealRelation convertToMealRelation(MealRelationDto dto) {
        return MealRelation.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public MealRelationDto convertToMealRelationDto(MealRelation entity) {
        return MealRelationDto.builder()
                .relationsId(entity.getRelationsId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public Units convertToUnits(UnitsDto entity) {
        return Units.builder()
                .unitId(entity.getUnitId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public UnitsDto convertToUnitsDto(Units entity) {
        return UnitsDto.builder()
                .unitId(entity.getUnitId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public DrugDto convertToDrugDto(Drug drug){
        return DrugDto.builder()
                .drugId(drug.getDrugId())
                .name(drug.getName())
                .genericName(drug.getGenericName())
                .description(drug.getDescription())
                .packing(drug.getPacking())
                .sideEffects(drug.getSideEffects())
                .contraindication(drug.getContraindication())
                .allergyInfo(drug.getAllergyInfo())
                .build();
    }

    public Drug convertToDrug(DrugDto dto){
        return Drug.builder()
                .name(dto.getName())
                .genericName(dto.getGenericName())
                .description(dto.getDescription())
                .packing(dto.getPacking())
                .sideEffects(dto.getSideEffects())
                .contraindication(dto.getContraindication())
                .allergyInfo(dto.getAllergyInfo())
                .build();
    }

    public PerscriptionDto convertToPerscriptionDto(Perscriptions perscriptions){
        PerscriptionDto perscriptionDto = PerscriptionDto.builder()
                .perscriptionId(perscriptions.getPerscriptionId())
                .customDrugName(perscriptions.getCustomDrugName())
                .dosage(perscriptions.getDosage())
                .frequency(perscriptions.getFrequency())
                .duration(perscriptions.getDuration())
                .instructions(perscriptions.getInstructions())
                .build();
        if( perscriptions.getDrugId() != null){
            perscriptionDto.setDrugId(convertToDrugDto(perscriptions.getDrugId()));
        }
        if( perscriptions.getUnitDosageId() != null){
            perscriptionDto.setUnitDosageId(convertToUnitsDto(perscriptions.getUnitDosageId()));
        }
        if(!perscriptions.getPerscriptionTimes().isEmpty()){
            List<DosageTimeDto> dosageTimeDtos = perscriptions.getPerscriptionTimes().stream().map( (item) -> {
                return convertToDosageTimeDto(item.getTimeId());
            }).toList();
            perscriptionDto.setDosageTimeDtos(dosageTimeDtos);
        }
        if( perscriptions.getMealRelation() != null) {
            perscriptionDto.setMealRelation(convertToMealRelationDto(perscriptions.getMealRelation()));
        }
        return perscriptionDto;
    }

    public Perscriptions convertToPerscription(PerscriptionDto dto){
        return Perscriptions.builder()
                .customDrugName(dto.getCustomDrugName())
                .dosage(dto.getDosage())
                .frequency(dto.getFrequency())
                .duration(dto.getDuration())
                .instructions(dto.getInstructions())
                .build();
    }

    public PaymentDto convertToPaymentDto(Payments payments){
        if(payments == null) return null;
        return PaymentDto.builder()
                .paymentId(payments.getPaymentId())
                .paymentMethod(payments.getPaymentMethod())
                .build();
    }

    public ICD10Dto convertICDToDto(ICD10Codes icd10Codes){
        return ICD10Dto.builder().description(icd10Codes.getDescription())
                .code(icd10Codes.getCode())
                .build();
    }

    public AppointmentRecordDto convertToAppointmentRecord(AppointmentRecord appointmentRecord){
        AppointmentRecordDto appointmentRecordDto = AppointmentRecordDto
                .builder()
                .recordId(appointmentRecord.getRecordId())
                .height(appointmentRecord.getHeight())
                .weight(appointmentRecord.getWeight())
                .bloodPressure(appointmentRecord.getBloodPressure())
                .temperature(appointmentRecord.getTemperature())
                .heartRate(appointmentRecord.getHeartRate())
                .spo2(appointmentRecord.getSpo2())
                .symptoms(appointmentRecord.getSymptoms())
                .initialDiagnosis(appointmentRecord.getInitialDiagnosis())
                .finalDiagnosis(appointmentRecord.getFinalDiagnosis())
                .notes(appointmentRecord.getNotes())
                .icd10(convertICDToDto(appointmentRecord.getIcd10Codes()))
                .followUpVisit(appointmentRecord.getFollowUpVisit() == null ? null : convertToFollowUpVisitDto(appointmentRecord.getFollowUpVisit()))
                .build();
        List<PerscriptionDto> perscriptionDtos = appointmentRecord.getPerscriptions().stream().map(this::convertToPerscriptionDto).toList();
        appointmentRecordDto.setPerscriptionDtos(perscriptionDtos);
        return appointmentRecordDto;
    }

    public FollowUpVisitDto convertToFollowUpVisitDto(FollowUpVisit followUpVisit){
        return FollowUpVisitDto.builder()
                .followUpId(followUpVisit.getFollowUpId())
                .followUpDate(followUpVisit.getFollowUpDate())
                .appointment(followUpVisit.getAppointment() != null ? AppointmentDto.builder()
                        .appointmentId(followUpVisit.getAppointment().getAppointmentId())
                        .build() : null)
                .instruction(followUpVisit.getInstruction())
                .build();
    }
}
