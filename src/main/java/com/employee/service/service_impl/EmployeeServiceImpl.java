package com.employee.service.service_impl;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;
import com.employee.enums.BloodGroup;
import com.employee.exception.custom_exception.EmployeeAlreadyExistsException;
import com.employee.exception.custom_exception.EmployeeNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee Not Found with ID: ";
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO employeeRequestDTO) {

        boolean exists = employeeRepository.existsByEmpEmailOrContactNoAndIsDeletedFalse(employeeRequestDTO.getEmpEmail(),employeeRequestDTO.getContactNo());
        if(exists){
            throw new EmployeeAlreadyExistsException("An employee already exists with the provided Email ID:- "+employeeRequestDTO.getEmpEmail()+" or Contact No.:-" +employeeRequestDTO.getContactNo());
        }
        Employee employee = modelMapper.map(employeeRequestDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public List<EmployeeResponseDTO> getAll() {
        List<Employee> employees = employeeRepository.findAllByIsDeletedFalse();
        if(employees.isEmpty()){
            throw new EmployeeNotFoundException("Employees Not Found!...");
        }
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeResponseDTO.class)).toList();
    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_MSG + id));
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_MSG + id));
        modelMapper.map(employeeRequestDTO,employee);
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_MSG + id));
        employee.setIsActive(false);
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseDTO patchUpdate(Long id, Map<String, Object> updates) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_MSG + id));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        updates.forEach((field,value)->{
            switch(field){
                case "empName" -> employee.setEmpName((String) value);
                case "empEmail" -> employee.setEmpEmail((String) value);
                case "contactNo" -> employee.setContactNo((String) value);
                case "address" -> employee.setAddress((String) value);
                case "salary" -> {
                    try {
                        if (value instanceof Number num) {
                            employee.setSalary(BigDecimal.valueOf(num.doubleValue()));
                        } else if (value instanceof String str) {
                            employee.setSalary(new BigDecimal(str.trim()));
                        } else {
                            throw new IllegalArgumentException("Salary must be number or numeric string");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid salary value: " + value);
                    }
                }

                case "birthDate"-> {
                    if (value instanceof String string) {
                        employee.setBirthDate(LocalDate.parse(string, dateFormatter));
                    }
                }

                case "bloodGroup" -> {
                    if (value instanceof String string) {
                        employee.setBloodGroup(BloodGroup.fromString(string));
                    } else if (value instanceof BloodGroup bloodGroup) {
                        employee.setBloodGroup(bloodGroup);
                    }
                }

                    default ->
                        throw new IllegalArgumentException("Field is Not Supported: "+ field);
            }
        });
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeResponseDTO.class);
    }
}
