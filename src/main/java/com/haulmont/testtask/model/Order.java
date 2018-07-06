package com.haulmont.testtask.model;

import javax.persistence.*;
import java.sql.Statement;
import java.util.Date;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.getAll", query = "SELECT o from Order o")
public class Order {

    public enum OrderStatus{
        PLANNED, COMPLETED, ACCEPTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /* @Column(name = "cost")
    private Double cost; */

    @Column(name = "order_status")
    private OrderStatus orderStatus;



    public Order() {
    }

    public Order(String description, Mechanic mechanic, Client client, Date startDate, Date endDate, OrderStatus orderStatus) {
        this.description = description;
        this.mechanic = mechanic;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order #" + getId() + ": " + description;
    }
}


