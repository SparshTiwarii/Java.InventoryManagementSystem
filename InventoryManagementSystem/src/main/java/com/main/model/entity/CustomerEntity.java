package com.main.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class CustomerEntity implements ParentEntity {
    private int idAddress;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCustomer;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;

    public CustomerEntity(int idAddress, String name, String phone, String email, String description) {
        this.idAddress = idAddress;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
    }

    public CustomerEntity() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone2) {
        this.email = phone2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void assignEntity(String[] params) {
        setIdAddress(Integer.parseInt(params[0]));
        setName(Objects.requireNonNull(params[1], "Name cannot be null"));
        setPhone(params[2] != null ? params[2] : "Empty");
        setEmail(params[2] != null ? params[3] : "Empty");
        setDescription(params[3] != null ? params[4] : "Empty");
    }
}
