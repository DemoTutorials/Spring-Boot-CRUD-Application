package com.employee.dto;

import com.employee.enums.BloodGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDTO {
    private String empName;
    private String empEmail;
    private String contactNo;
    private String address;
    private BigDecimal salary;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MMM-yyyy")
    private LocalDate birthDate;
    private BloodGroup bloodGroup;


    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(String empName, String empEmail, String contactNo, String address, BigDecimal salary, LocalDate birthDate, BloodGroup bloodGroup) {
        this.empName = empName;
        this.empEmail = empEmail;
        this.contactNo = contactNo;
        this.address = address;
        this.salary = salary;
        this.birthDate = birthDate;
        this.bloodGroup = bloodGroup;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return "EmployeeResponseDTO{" +
                "empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                ", bloodGroup=" + bloodGroup +
                '}';
    }
}
