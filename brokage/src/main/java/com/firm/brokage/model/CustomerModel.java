package com.firm.brokage.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customer")
public class CustomerModel {

    private Long customerId;
    private String name;
    private String surname;
    private Date birthDate;

    public CustomerModel() {
    }

    public CustomerModel(Long customerId, String name, String surname, Date birthDate) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "seq_cst", allocationSize = 1, initialValue = 100001)
    @Column(name="customer_id", nullable = false, updatable = false)
    public Long getCustomerId() {
        return customerId;
    }

    @Column(name="name", nullable = false, updatable = false)
    public String getName() {
        return name;
    }

    @Column(name="surname", nullable = false, updatable = false)
    public String getSurname() {
        return surname;
    }

    @Column(name="bird", nullable = false, updatable = false)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
