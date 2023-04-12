package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.Column;


public class Client extends BaseEntity {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;


    public Client() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column()
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String fullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return String.format("""
                        %d. %s %s
                            Address: %s
                            PhoneNumber: %s
                        """, this.getId(), getFirstName(), getLastName(),
                getAddress() == null ? "none" : getAddress(),
                getPhoneNumber() == null ? "none" : getPhoneNumber());

    }
}
