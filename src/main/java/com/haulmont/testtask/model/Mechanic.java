package com.haulmont.testtask.model;

import javax.persistence.*;

@Entity
@Table(name = "mechanics")
@NamedQuery(name = "Mechanic.getAll", query = "SELECT m from Mechanic m")
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "tax")
    private Double tax;

    public Mechanic() {
    }

    public Mechanic(String firstname, String surname, String lastname, Double tax) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.tax = tax;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return surname + " " + firstname;
    }
}
