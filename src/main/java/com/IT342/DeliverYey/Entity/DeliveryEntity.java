package com.IT342.DeliverYey.Entity;

import com.IT342.DeliverYey.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbldelivery")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "DeliveryTime")
    private LocalDateTime deliveryTime;

    @Column(name = "DeliveryAddress")
    private String deliveryAddress;

    @Column(name = "Status")
    private String status;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.DELIVERY;

    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    public DeliveryEntity() {
        super();
    }

    public DeliveryEntity(LocalDateTime deliveryTime, String deliveryAddress, String status, String notes) {
        this.deliveryTime = deliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.notes = notes;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
