package com.IT342.DeliverYey.Entity;

import com.IT342.DeliverYey.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tblstudent")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private int sid;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String gender;
    private String email;
    private String contactInfo;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType = UserType.STUDENT;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private StaffEntity staff;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private List<OrderItemEntity> orderItem;

    public List<OrderItemEntity> getOrderItems() {
        return orderItem;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItem = orderItems;
    }

    @OneToOne(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private OrderEntity order;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private MenuEntity menu;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"student"})
    private DeliveryEntity delivery;

    public StudentEntity() {
        super();
    }

    public StudentEntity(int sid, String firstname, String lastname, String gender, String email, String password, UserType userType, boolean isDeleted) {
        this.sid = sid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.isDeleted = isDeleted;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<OrderItemEntity> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemEntity> orderItem) {
        this.orderItem = orderItem;
    }

    public StaffEntity getStaff() {
        return staff;
    }
    public void setStaff(StaffEntity savedStaff) {
    }

    public OrderEntity getOrder() {
        return order;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
    }

    public List<OrderItemEntity> getOrderRequests() {
        return orderItem;
    }

    public String getContactInfo() {
        return contactInfo;
    }


    public class OrderItemRequest {
        private int sid;
        public OrderItemEntity orderItem;

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public OrderItemEntity getAllOrderItem() {
            return orderItem;
        }

        public void setOrderItem(OrderItemEntity orderItem) {
            this.orderItem = orderItem;
        }
    }
}
