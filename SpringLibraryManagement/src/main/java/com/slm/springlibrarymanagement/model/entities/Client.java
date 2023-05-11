package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "clients")
@SequenceGenerator(name = "default_gen", sequenceName = "clients_seq", allocationSize = 1)
public class Client extends BaseEntity {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Set<Order> orders;

    public Client() {
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Column(nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}