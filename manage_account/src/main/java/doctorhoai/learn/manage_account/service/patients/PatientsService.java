package doctorhoai.learn.manage_account.service.patients;

import doctorhoai.learn.manage_account.dto.PatientsDto;
import doctorhoai.learn.manage_account.dto.PatientsRegister;
import doctorhoai.learn.manage_account.dto.UpdatePassword;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.PatientsFilter;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;

import java.util.List;
import java.util.UUID;


public interface PatientsService {
    PatientsDto registerPatient(final PatientsRegister patientsRegister);
    PatientsDto getPatientsById(final UUID id, AccountStatus status);
    PageObject getPatientsPage(final PatientsFilter filter);
    PatientsDto updatePatient(final UUID id, final PatientsRegister register);
    PatientsDto getPatientsByPhone(final String phone, final AccountStatus status);
    List<PatientsDto> getPatientsByIds(List<UUID> ids);
    boolean checkNumberPhone(String phone);
    PatientsDto updatePassword(UUID id, UpdatePassword password);
    PatientsDto getPatientByUsername(String username);
    List<PatientsDto> getAllPatients(List<UUID> ids, String search);
    PatientsDto updatePasswordByOpt(String phone, String password);
}
