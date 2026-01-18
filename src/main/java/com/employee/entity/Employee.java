package com.employee.entity;

import com.employee.enums.BloodGroup;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name",nullable = false)
    private String empName;

    @Column(name = "Email",nullable = false,unique = true)
    private String empEmail;

    @Column(name = "Contact",nullable = false,unique = true)
    private String contactNo;

    @Column(name = "Address",nullable = false,columnDefinition = "TEXT")
    private String address;

    @Column(name = "Salary",nullable = false)
    private BigDecimal salary;

    //@Temporal(TemporalType.DATE)
    @Column(name = "Birth_Date",nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "BloodGroup",nullable = false)
    private BloodGroup bloodGroup;

    @Column(name = "Deleted")
    private Boolean isDeleted=false;

    @Column(name = "Active")
    private Boolean isActive=true;

    @CreatedDate
    @Column(name = "Created_DateTime")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "Updated_DateTime")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void create(){
    createdAt=LocalDateTime.now();
    }

    @PreUpdate
    protected void update(){
        updatedAt=LocalDateTime.now();
    }

    public Employee() {
    }

    public Employee(Long id, String empName, String empEmail, String contactNo, String address, BigDecimal salary, LocalDate birthDate, BloodGroup bloodGroup, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.empName = empName;
        this.empEmail = empEmail;
        this.contactNo = contactNo;
        this.address = address;
        this.salary = salary;
        this.birthDate = birthDate;
        this.bloodGroup = bloodGroup;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                ", bloodGroup=" + bloodGroup +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

