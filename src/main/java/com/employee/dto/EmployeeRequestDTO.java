package com.employee.dto;

import com.employee.enums.BloodGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is Required")
    private String empName;

    @NotBlank(message = "Email is Required")
    private String empEmail;

    @NotBlank(message = "Contact No is Required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String contactNo;

    @NotBlank(message = "Address is Required")
    private String address;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
   // @PositiveOrZero(message = "Salary cannot be negative")
    private BigDecimal salary;

    @NotNull(message = "BirthDate is Required")
    @Past(message = "Birth date must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MMM-yyyy")
    private LocalDate birthDate;

    @NotNull(message = "Blood Group is required")
    //@ValidBloodGroup(message = "Invalid blood group. Allowed: A_Positive, A_Negative, B_Positive, ...")
    private BloodGroup bloodGroup;

    public EmployeeRequestDTO() {
    }

    public EmployeeRequestDTO(String empName, String empEmail, String contactNo, String address, BigDecimal salary, LocalDate birthDate, BloodGroup bloodGroup) {
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
        return "EmployeeRequestDTO{" +
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
