package com.IT342.DeliverYey.Controller;

import com.IT342.DeliverYey.Entity.OrderItemEntity;
import com.IT342.DeliverYey.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderitem")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/insertOrderItem")
    public OrderItemEntity insertOrderItem(@RequestBody OrderItemEntity orderItem) {
        return orderItemService.insertOrderItem(orderItem);
    }

    @GetMapping("/getAllOrderItem")
    public List<OrderItemEntity> getAllOrderItem() {
        return orderItemService.getAllOrderItem();
    }

    @PutMapping("/updateOrderItem")
    public OrderItemEntity updateOrderItem(@RequestParam int orderItemId, @RequestBody OrderItemEntity newOrderItemDetails) {
        return orderItemService.updateOrderItem(orderItemId, newOrderItemDetails);
    }

    @DeleteMapping("/deleteOrderItem/{requestId}")
    public String deleteOrderItem(@PathVariable int requestId) {
        return orderItemService.deleteOrderItem(requestId);
    }
}
