package com.IT342.DeliverYey.Service;

import com.IT342.DeliverYey.Entity.OrderEntity;
import com.IT342.DeliverYey.Entity.OrderItemEntity;
import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Repository.OrderItemRepository;
import com.IT342.DeliverYey.Repository.StudentRepository;
import com.IT342.DeliverYey.UserType;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public OrderItemEntity insertOrderItem(OrderItemEntity orderItem) {
        StudentEntity student = orderItem.getStudent();

        // Check if student is not null before updating
        if (student != null) {
            // Update the student's user type and save it
            student.setUserType(UserType.ORDERITEM);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Associated student cannot be null.");
        }

        // Return the updated order item
        return orderItem;
    }


    public List<OrderItemEntity> getAllOrderItem() {
        List<OrderItemEntity> orderItem = orderItemRepository.findAll();

        orderItem.forEach(orderItemEntity -> {
            Hibernate.initialize(orderItemEntity.getStudent());
        });

        return orderItem;
    }

    @Transactional
    public OrderItemEntity updateOrderItem(int orderItemId, OrderItemEntity newOrderItemDetails) {
        OrderItemEntity orderItem = new OrderItemEntity();
        try {
            //1. search the ID  number of the student that will be updated
            orderItem = orderItemRepository.findById(orderItemId).get();

            //2. update the record
            orderItem.setOrderType(newOrderItemDetails.getOrderType());
            orderItem.setQuantity(newOrderItemDetails.getQuantity());
            orderItem.setRequestDate(newOrderItemDetails.getRequestDate());
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("Student" + orderItemId + "does not exist!");
        }finally {
            return orderItemRepository.save(orderItem);
        }
    }

    public String deleteOrderItem(int orderRequestId) {
        try {
            Optional<OrderItemEntity> optionalOrderRequest = orderItemRepository.findById(orderRequestId);

            if (optionalOrderRequest.isPresent()) {
                OrderItemEntity orderRequest = optionalOrderRequest.get();
                StudentEntity student = orderRequest.getStudent();

                if (student != null) {
                    // Remove order request from student's order requests
                    student.getOrderRequests().remove(orderRequest);

                    // Save the student without modifying other properties
                    studentRepository.save(student);
                }

                // Delete the order request
                orderItemRepository.deleteById(orderRequestId);

                return "Order Request " + orderRequestId + " is successfully deleted!";
            } else {
                return "Order Request " + orderRequestId + " does not exist.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete order request.", e);
        }
    }

    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
