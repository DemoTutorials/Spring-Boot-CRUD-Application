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
        Employee newEmployee = employeeRepository.save(employee);
        return modelMapper.map(newEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public List<EmployeeResponseDTO> getAll() {
        List<Employee> employeeList = employeeRepository.findAllByIsDeletedFalse();
        if(employeeList.isEmpty()){
            throw new EmployeeNotFoundException("Employees Not Found!...");
        }
        List<EmployeeResponseDTO> list = employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeResponseDTO.class)).toList();
        return list;
    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID:- " + id));
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID:- " + id));
        modelMapper.map(employeeRequestDTO,employee);
        Employee newEmployee = employeeRepository.save(employee);
        return modelMapper.map(newEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID:- " + id));
        employee.setActive(false);
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseDTO patchUpdate(Long id, Map<String, Object> updates) {
        Employee employee = employeeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found with ID:- " + id));
        updates.forEach((field,value)->{
            switch(field){
                case "empName": employee.setEmpName((String) value);
                    break;

                case "empEmail": employee.setEmpEmail((String) value);
                    break;

                case "contactNo": employee.setContactNo((String) value);
                    break;

                case "address": employee.setAddress((String) value);
                    break;

                case "salary":
                    if(value instanceof BigDecimal){
                    employee.setSalary((BigDecimal) value);
                    }
                    break;

                case "birthDate":
                    if(value instanceof String){
                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                        employee.setBirthDate(LocalDate.parse((String)value,formatter));
                    }
                    break;

                case "bloodGroup":
                    if(value instanceof String){
                        employee.setBloodGroup(BloodGroup.fromString((String) value));
                    }
                    else if(value instanceof BloodGroup){
                        employee.setBloodGroup((BloodGroup) value);
                    }
                    break;

                    default:
                        throw new IllegalArgumentException("Filed is Not Supported");
            }
        });
        Employee newEmployee = employeeRepository.save(employee);
        return modelMapper.map(newEmployee, EmployeeResponseDTO.class);
    }
}
