package doctorhoai.learn.manage_account.service.employee;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.EmployeeRegister;
import doctorhoai.learn.manage_account.dto.UpdatePassword;
import doctorhoai.learn.manage_account.dto.filter.EmployeesFilter;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.exception.exception.EmployeeNotFoundException;
import doctorhoai.learn.manage_account.exception.exception.PasswordNotCorrect;
import doctorhoai.learn.manage_account.exception.exception.RoomNotFoundException;
import doctorhoai.learn.manage_account.mapper.CommonFunction;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.*;
import doctorhoai.learn.manage_account.model.Account.Account;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.repository.*;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeesRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final RoomsRepository roomsRepository;
    private final SpecializationsRepository specializationsRepository;
    private final AccountRepository accountRepository;
    private final Mapper mapper;
    private final ServiceRepository serviceRepository;
    private final PatientsRepository patientsRepository;
    private final EmployeesRepository employeesRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final CommonFunction commonFunction;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeRegister register) {
        // Check existing phone number
        Optional<Account> accountCheck = accountRepository.findByPhoneNumber(register.getPhoneNumber());
        if (accountCheck.isPresent()) {
            throw new BadException("Phone number already exists");
        }

        Optional<Patients> citizenPatientCheck = patientsRepository.findByCitizenId(register.getCitizenId());
        Optional<Employees> citizenEmployeeCheck = employeeRepository.findByCitizenId(register.getCitizenId());
        if (citizenPatientCheck.isPresent() || citizenEmployeeCheck.isPresent()) {
            throw new BadException("Citizen ID already exists");
        }

        Optional<Employees> emailEmployee = employeeRepository.getEmployeesByEmail(register.getEmail());
        if( emailEmployee.isPresent()){
            throw new BadException("Email already exists");
        }

        // Validate and get all required entities
        Role role = roleRepository.findByRoleId(register.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        
        Rooms rooms = roomsRepository.findByRoomId(register.getRoom())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        Specializations specializations = specializationsRepository.findBySpecializationId(register.getSpecialization())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        // Create and save Account first
        Account account = Account.builder()
                .roleId(role)
                .phoneNumber(register.getPhoneNumber())
                .password(passwordEncoder.encode(register.getPassword()))
                .status(register.getStatus())
                .build();
        account = accountRepository.save(account);

        // Create Employee with the saved Account
        final Employees employeeEntity = Employees.builder()
                .accountId(account)
                .roomId(rooms)
                .specializationId(specializations)
                .fullName(register.getFullName())
                .citizenId(register.getCitizenId())
                .profile(register.getProfile())
                .dob(register.getDob())
                .gender(register.isGender())
                .address(register.getAddress())
                .avatar(register.getAvatar())
                .hiredDate(register.getHiredDate())
                .email(register.getEmail())
                .build();

        // Validate and prepare services
        List<UUID> serviceIds = register.getServices();
        if (serviceIds != null && !serviceIds.isEmpty()) {
            List<Services> services = serviceRepository.findByServiceIdIn(serviceIds);
            if (services.size() != serviceIds.size()) {
                throw new RuntimeException("Some services not found");
            }

            List<EmployeeServices> employeeServices = services.stream()
                    .map(service -> {
                        EmployeeServices es = new EmployeeServices();
                        es.setServices(service);
                        es.setEmployees(employeeEntity);
                        return es;
                    })
                    .toList();
            employeeEntity.setEmployeeServices(employeeServices);
        }

        // Save employee with services
        Employees savedEmployee = employeeRepository.save(employeeEntity);
        return mapper.convertToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(UUID id, AccountStatus status) {
        Employees employees = employeeRepository.findByEmployeeIdAndStatus(id, status).orElseThrow(EmployeeNotFoundException::new);
        return mapper.convertToEmployeeDto(employees);
    }

    @Override
    public EmployeeDto updateEmployee(UUID id, EmployeeRegister register) {
        Employees employees = employeeRepository.findByEmployeeIdAndStatus(id, AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        // Check existing phone number
        Optional<Account> accountCheck = accountRepository.findByPhoneNumber(register.getPhoneNumber());
        if (accountCheck.isPresent()) {
            Optional<Employees> employeeCheck = employeeRepository.getEmployeesByAccountId_AccountId(accountCheck.get().getAccountId());
            if( employeeCheck.isPresent() ){
                if( !employeeCheck.get().getEmployeeId().equals(id)){
                    throw new BadException("Phone number already exists");
                }
            }
            Optional<Patients> patients = patientsRepository.getPatientsByAccountId_AccountId(accountCheck.get().getAccountId());
            if( patients.isPresent()){
                    throw new BadException("Phone number already exists");
            }
        }

        Optional<Patients> citizenPatientCheck = patientsRepository.findByCitizenId(register.getCitizenId());
        Optional<Employees> citizenEmployeeCheck = employeeRepository.findByCitizenId(register.getCitizenId());
        if (citizenPatientCheck.isPresent() || citizenEmployeeCheck.isPresent()) {
            if( citizenEmployeeCheck.isPresent() && !citizenEmployeeCheck.get().getEmployeeId().equals(id))
            {
                throw new BadException("Citizen ID already exists");
            }
            if( citizenPatientCheck.isPresent()){
                throw new BadException("Citizen ID already exists");
            }
        }

        Optional<Employees> emailEmployee = employeeRepository.getEmployeesByEmail(register.getEmail());
        if( emailEmployee.isPresent()){
            if( !emailEmployee.get().getEmployeeId().equals(id)){
                throw new BadException("Email already exists");
            }
        }

        employees.setFullName(register.getFullName());
        employees.setCitizenId(register.getCitizenId());
        employees.setDob(register.getDob());
        employees.setGender(register.isGender());
        employees.setAddress(register.getAddress());
        employees.setAvatar(register.getAvatar());
        employees.setHiredDate(register.getHiredDate());
        employees.setEmail(register.getEmail());
        employees.getAccountId().setStatus(register.getStatus());
        if( employees.getRoomId().getRoomId() != register.getRoom()){
            Rooms rooms = roomsRepository.findByRoomId(register.getRoom()).orElseThrow(RoomNotFoundException::new);
            employees.setRoomId(rooms);
        }
        if( employees.getSpecializationId().getSpecializationId() != register.getSpecialization()){
            Specializations specializations = specializationsRepository.findBySpecializationId(register.getSpecialization()).orElseThrow(() -> new RuntimeException("Specialization not found"));
            employees.setSpecializationId(specializations);
        }
        // ====== XỬ LÝ SERVICES ======
        // Danh sách service mới client gửi lên
        List<UUID> newServiceIds = register.getServices();

        // Service hiện tại của employee
        List<EmployeeServices> currentEmployeeServices = employees.getEmployeeServices();

        // Lấy set id service hiện tại
        Set<UUID> currentServiceIds = currentEmployeeServices.stream()
                .map(es -> es.getServices().getServiceId())
                .collect(Collectors.toSet());

        // 1. Xóa service không còn trong newServiceIds
        currentEmployeeServices.removeIf(es -> !newServiceIds.contains(es.getServices().getServiceId()));

        // 2. Thêm service mới chưa có
        for (UUID serviceId : newServiceIds) {
            if (!currentServiceIds.contains(serviceId)) {
                Services service = serviceRepository.getServicesByServiceId(serviceId)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

                EmployeeServices employeeService = new EmployeeServices();
                employeeService.setEmployees(employees);
                employeeService.setServices(service);

                currentEmployeeServices.add(employeeService);
            }
        }

        employees.setEmployeeServices(currentEmployeeServices);
        employees = employeeRepository.save(employees);
        return mapper.convertToEmployeeDto(employees);
    }

    @Override
    public EmployeeDto getEmployeeByPhone(String phone, AccountStatus status) {
        Employees employees = employeeRepository.getEmployeesByPhoneAndStatus(phone, status).orElseThrow(EmployeeNotFoundException::new);
        return mapper.convertToEmployeeDto(employees);
    }

    @Override
    public void deleteEmployee(UUID id) {
        Employees employees = employeeRepository.findByEmployeeIdAndStatus(id, AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        employees.getAccountId().setStatus(AccountStatus.DELETE);
        employeeRepository.save(employees);
    }

    @Override
    public PageObject getAllEmployees(EmployeesFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<Employees> page = employeeRepository.getEmployeesByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                filter.getSpecializations(),
                filter.getRooms(),
                filter.getServices(),
                filter.getIsActive(),
                pageable
        );
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(page.getTotalPages())
                .data(page.getContent().stream().map(mapper::convertToEmployeeDto).toList())
                .build();
    }

    @Override
    public List<EmployeeDto> getEmployeeByIds(List<UUID> ids) {
        List<Employees> employees = employeeRepository.getEmployeesByEmployeeIdIn(ids);
        return employees.stream().map(mapper::convertToEmployeeDto).toList();
    }

    @Override
    public EmployeeDto updatePassword(UUID id, UpdatePassword updatePassword) {
        Employees employees = employeeRepository.findByEmployeeIdAndStatus(id, AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        boolean match = passwordEncoder.matches(updatePassword.getOldPass(), employees.getAccountId().getPassword());
        if( match){
            employees.getAccountId().setPassword(passwordEncoder.encode(updatePassword.getNewPass()));
            employeeRepository.save(employees);
            return mapper.convertToEmployeeDto(employees);
        } else {
            throw new PasswordNotCorrect();
        }
    }

    @Override
    public EmployeeDto getEmployeeByUsername(String username) {
        Employees employees = employeeRepository.getEmployeesByAccountId_PhoneNumberAndAccountId_Status(username, AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        return mapper.convertToEmployeeDto(employees);
    }

    @Override
    public EmployeeDto updatePasswordByOPT(String password, String phone) {
        Employees employees = employeeRepository.getEmployeesByPhoneAndStatus(phone, AccountStatus.ACTIVE).orElseThrow(EmployeeNotFoundException::new);
        employees.getAccountId().setPassword(passwordEncoder.encode(password));
        employeeRepository.save(employees);
        return mapper.convertToEmployeeDto(employees);
    }

    @Override
    public EmployeeDto resetPassword(String phone) {
        Employees employees = employeeRepository.getEmployeesByPhoneAndStatus(phone, AccountStatus.ACTIVE)
                .orElseThrow(EmployeeNotFoundException::new);
        employees.getAccountId().setPassword(passwordEncoder.encode("@Hoai100303"));
        employeesRepository.save(employees);
        return mapper.convertToEmployeeDto(employees);
    }

}
