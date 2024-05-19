package com.IT342.DeliverYey.Entity;

import com.IT342.DeliverYey.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "tblorder")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "contact_information")
    private String contactInfo;

    private String location;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.ORDER;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid", referencedColumnName = "sid", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private StudentEntity student;

    public OrderEntity() {
        super();
    }

    public OrderEntity(int orderId, String orderType, String contactInfo, String location, StudentEntity student) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.contactInfo = contactInfo;
        this.location = location;
        this.student = student;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
