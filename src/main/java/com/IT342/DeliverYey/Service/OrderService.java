package com.IT342.DeliverYey.Service;

import com.IT342.DeliverYey.Entity.OrderEntity;
import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Repository.OrderRepository;
import com.IT342.DeliverYey.Repository.StudentRepository;
import com.IT342.DeliverYey.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OrderEntity insertOrder(OrderEntity order) {
        try {
            // Fetch the student from the repository by its ID
            StudentEntity student = studentRepository.findById(order.getStudent().getSid())
                    .orElseThrow(() -> new NoSuchElementException("Student with ID " + order.getStudent().getSid() + " not found."));

            // Check if the student is associated with a menu or delivery
            if (student.getMenu() != null || student.getDelivery() != null) {
                throw new IllegalStateException("Student cannot order if associated with a menu or delivery.");
            }

            // Validate the contact info
            if (!isValidContactInfo(order.getContactInfo())) {
                throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
            }

            // Update the student's user type and save it
            student.setUserType(UserType.ORDER);
            studentRepository.save(student);

            // Set the student for the order and save the order
            order.setStudent(student);
            OrderEntity savedOrder = orderRepository.save(order);

            // Assuming you have a setOrder method in StudentEntity to set the order for the student
            student.setOrder(savedOrder);
            studentRepository.save(student);

            return savedOrder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert order.", e);
        }
    }

    public List<OrderEntity> getAllOrder() {
        return orderRepository.findAll();
    }

    public OrderEntity updateOrder(int orderId, OrderEntity newOrderDetails) {
        OrderEntity order = new OrderEntity();
        try {
            //1. search the ID  number of the student that will be updated
            order = orderRepository.findById(orderId).get();

            //2. update the record
            order.setOrderType(newOrderDetails.getOrderType());
            order.setContactInfo(newOrderDetails.getContactInfo());
            order.setLocation(newOrderDetails.getLocation());
            order.setPaymentMethod(newOrderDetails.getPaymentMethod());
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Student" + orderId + "does not exist!");
        } finally {
            return orderRepository.save(order);
        }
    }


    public String deleteOrder(int orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            StudentEntity student = order.getStudent();

            if (student != null) {
                // Remove the reference to the order and update the user type
                student.setOrder(null);
                student.setUserType(UserType.STUDENT);
                studentRepository.save(student);
            }

            // Delete the order
            orderRepository.deleteById(orderId);
            return "Order " + orderId + " is successfully deleted!";
        } else {
            return "Order " + orderId + " does not exist.";
        }
    }

    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
