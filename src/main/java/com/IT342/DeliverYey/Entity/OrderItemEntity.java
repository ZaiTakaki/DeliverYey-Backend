package com.IT342.DeliverYey.Entity;

import com.IT342.DeliverYey.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tblorderitem")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitem_id")
    private int orderItemId;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "request_date")
    private String requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.ORDERITEM;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = true)
    @JsonIgnoreProperties("orderItems")
    private StudentEntity student;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private List<OrderItemEntity> orderItems;


    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    public OrderItemEntity() {
        super();
    }

    public OrderItemEntity(int orderItemId, String orderType, int quantity, String requestDate, StudentEntity student) {
        this.orderItemId = orderItemId;
        this.orderType = orderType;
        this.quantity = quantity;
        this.requestDate = requestDate;
        this.student = student;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
