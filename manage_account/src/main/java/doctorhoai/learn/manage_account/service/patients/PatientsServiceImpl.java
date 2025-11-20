package doctorhoai.learn.manage_account.service.patients;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.PatientsDto;
import doctorhoai.learn.manage_account.dto.PatientsRegister;
import doctorhoai.learn.manage_account.dto.UpdatePassword;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.PatientsFilter;
import doctorhoai.learn.manage_account.exception.exception.PasswordNotCorrect;
import doctorhoai.learn.manage_account.exception.exception.PatientsNotFoundException;
import doctorhoai.learn.manage_account.exception.exception.RoleNotFoundException;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.Account.Account;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.model.Employees;
import doctorhoai.learn.manage_account.model.Patients;
import doctorhoai.learn.manage_account.model.Role;
import doctorhoai.learn.manage_account.repository.AccountRepository;
import doctorhoai.learn.manage_account.repository.EmployeesRepository;
import doctorhoai.learn.manage_account.repository.PatientsRepository;
import doctorhoai.learn.manage_account.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {
    // import
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PatientsRepository patientsRepository;
    private final EmployeesRepository employeeRepository;
    private final Mapper mapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * register patient
     * @param patientsRegister data for register
     * @return patient register
     */
    @Override
    @Transactional
    public PatientsDto registerPatient(final PatientsRegister patientsRegister) {
        log.info("**Service : register patients**");
        Optional<Account> accountCheck = accountRepository.findByPhoneNumber(patientsRegister.getPhoneNumber());
        if (accountCheck.isPresent()) {
            throw new BadException("Phone number already exists");
        }

        Optional<Patients> citizenPatientCheck = patientsRepository.findByCitizenId(patientsRegister.getCitizenId());
        Optional<Employees> citizenEmployeeCheck = employeeRepository.findByCitizenId(patientsRegister.getCitizenId());
        if (citizenPatientCheck.isPresent() || citizenEmployeeCheck.isPresent()) {
            throw new BadException("Citizen ID already exists");
        }

        Optional<Patients> insurancePatientCheck = patientsRepository.findByInsuranceCode(patientsRegister.getInsuranceCode());
        if (insurancePatientCheck.isPresent()) {
            throw new BadException("Insurance code already exists");
        }

        // check role
        Role role = roleRepository.findByRoleId(patientsRegister.getRoleId()).orElseThrow(RoleNotFoundException::new);
        // map to account
        Account account = Account.builder()
                .roleId(role)
                .phoneNumber(patientsRegister.getPhoneNumber())
                .password(passwordEncoder.encode(patientsRegister.getPassword()))
                .status(patientsRegister.getStatus())
                .build();
        // map to account
        Patients patients = Patients.builder()
                .accountId(account)
                .fullName(patientsRegister.getFullName())
                .dob(patientsRegister.getDob())
                .gender(patientsRegister.isGender())
                .address(patientsRegister.getAddress())
                .insuranceCode(patientsRegister.getInsuranceCode())
                .emergencyContact(patientsRegister.getEmergencyContact())
                .citizenId(patientsRegister.getCitizenId())
                .job(patientsRegister.getJob())
                .build();
        Patients patientsSaved = patientsRepository.save(patients);


        return PatientsDto.builder()
                .patientId(patientsSaved.getPatientId())
                .fullName(patientsSaved.getFullName())
                .dob(patientsSaved.getDob())
                .gender(patientsSaved.isGender())
                .address(patientsSaved.getAddress())
                .insuranceCode(patientsSaved.getInsuranceCode())
                .emergencyContact(patientsSaved.getEmergencyContact())
                .citizenId(patientsSaved.getCitizenId())
                .job(patientsSaved.getJob())
                .phoneNumber(account.getPhoneNumber())
                .status(account.getStatus())
                .nameRole(role.getNameRole())
                .build();
    }

    /**
     * get patient by id
     * @param id - id patient
     * @param status - status account
     * @return - patient dto
     */
    @Override
    public PatientsDto getPatientsById(UUID id, AccountStatus status) {
        log.info("**Service : get patients by id**");
        // get patients
        Patients patients = patientsRepository.getPatientsByPatientId(id, status).orElseThrow(PatientsNotFoundException::new);
        // convert to dto
        return mapper.convertPatientDto(patients);
    }

    /**
     * get patient list by filter
     * @param filter - filter patient
     * @return - patient page
     */
    @Override
    public PageObject getPatientsPage(PatientsFilter filter) {
        log.info("**Service : get patients by filter**");
        // get pageable
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
        }
        // get data page
        Page<Patients> patientsPage = patientsRepository.getPatientsByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                filter.getIsActive(),
                pageable
        );

        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(patientsPage.getTotalPages())
                .data(patientsPage.getContent().stream().map(mapper::convertPatientDto).toList())
                .build();
    }

    /**
     * update patient by id
     * @param id - id patient
     * @param register - info patient to update
     * @return - patient after update
     */
    @Override
    public PatientsDto updatePatient(UUID id, PatientsRegister register) {

        Optional<Account> accountCheck = accountRepository.findByPhoneNumber(register.getPhoneNumber());
        if (accountCheck.isPresent()) {
            Optional<Employees> employees = employeeRepository.getEmployeesByAccountId_AccountId(accountCheck.get().getAccountId());
            if( employees.isPresent() ){
                throw new BadException("Phone number already exists");
            }
            Optional<Patients> patients = patientsRepository.getPatientsByAccountId_AccountId(accountCheck.get().getAccountId());
            if( patients.isPresent()){
                if( !patients.get().getPatientId().equals(id) ){
                    throw new BadException("Phone number already exists");
                }
            }
        }

        Optional<Patients> citizenPatientCheck = patientsRepository.findByCitizenId(register.getCitizenId());
        Optional<Employees> citizenEmployeeCheck = employeeRepository.findByCitizenId(register.getCitizenId());
        if (citizenPatientCheck.isPresent() || citizenEmployeeCheck.isPresent()) {
            if( citizenPatientCheck.isPresent() && !citizenPatientCheck.get().getPatientId().equals(id)){
                throw new BadException("Citizen ID already exists");
            }
            if( citizenEmployeeCheck.isPresent()){
                throw new BadException("Citizen ID already exists");
            }
        }

        Optional<Patients> insurancePatientCheck = patientsRepository.findByInsuranceCode(register.getInsuranceCode());
        if (insurancePatientCheck.isPresent()) {
           if( !insurancePatientCheck.get().getPatientId().equals(id)){
               throw new BadException("Insurance code already exists");
           }
        }
        log.info("**Service : update patients by id**");
        // get patient by id
        Patients patients = patientsRepository.getPatientsByPatientId(id, AccountStatus.ACTIVE).orElseThrow(PatientsNotFoundException::new);
        // mapping update
        patients.setFullName(register.getFullName());
        patients.setDob(register.getDob());
        patients.setGender(register.isGender());
        patients.setAddress(register.getAddress());
        patients.setInsuranceCode(register.getInsuranceCode());
        patients.setEmergencyContact(register.getEmergencyContact());
        patients.setCitizenId(register.getCitizenId());
        patients.setJob(register.getJob());
        patients.getAccountId().setPhoneNumber(register.getPhoneNumber());
        patients.getAccountId().setStatus(register.getStatus());
        // save update
        Patients patientSaved = patientsRepository.save(patients);
        return mapper.convertPatientDto(patientSaved);
    }

    /**
     * get patient by phone number
     * @param phone - phone number
     * @return - patient
     */
    @Override
    public PatientsDto getPatientsByPhone(String phone, AccountStatus status) {
        log.info("**Service : get patient by phone**");
        // get patient
        Patients patients = patientsRepository.getPatientsByPhoneAndStatus(phone, status).orElseThrow(PatientsNotFoundException::new);
        // mapping to dto
        return mapper.convertPatientDto(patients);
    }

    @Override
    public List<PatientsDto> getPatientsByIds(List<UUID> ids) {
        List<Patients> patients = patientsRepository.getPatientsByPatientIdIn(ids);
        return patients.stream().map(mapper::convertPatientDto).toList();
    }

    @Override
    public boolean checkNumberPhone(String phone) {
        Optional<Account> accountOptional = accountRepository.findByPhoneNumber(phone);
        if( accountOptional.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public PatientsDto updatePassword(UUID id, UpdatePassword password) {
        Patients patients = patientsRepository.getPatientsByPatientId(id, AccountStatus.ACTIVE).orElseThrow(PatientsNotFoundException::new);
        boolean match = passwordEncoder.matches(password.getOldPass(), patients.getAccountId().getPassword());
        if( match){
            patients.getAccountId().setPassword(passwordEncoder.encode(password.getNewPass()));
            patientsRepository.save(patients);
            return mapper.convertPatientDto(patients);
        } else {
            throw new PasswordNotCorrect();
        }
    }

    @Override
    public PatientsDto getPatientByUsername(String username) {
        Patients patients = patientsRepository.getPatientsByAccountId_PhoneNumberAndAccountId_Status(username, AccountStatus.ACTIVE)
                .orElseThrow(PatientsNotFoundException::new);
        return mapper.convertPatientDto(patients);
    }

    @Override
    public List<PatientsDto> getAllPatients(List<UUID> ids, String search) {
        List<Patients> patients = patientsRepository.findAllByPatientIdIn(ids, search);
        return patients.stream().map(mapper::convertPatientDto).toList();
    }

    @Override
    public PatientsDto updatePasswordByOpt(String phone, String password) {
        Patients patients = patientsRepository.getPatientsByPhoneAndStatus(phone, AccountStatus.ACTIVE)
                .orElseThrow(PatientsNotFoundException::new);
        patients.getAccountId().setPassword(passwordEncoder.encode(password));
        patientsRepository.save(patients);
        return mapper.convertPatientDto(patients);
    }
}
