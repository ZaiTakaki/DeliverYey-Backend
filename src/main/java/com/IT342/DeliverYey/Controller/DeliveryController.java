package com.IT342.DeliverYey.Controller;

import com.IT342.DeliverYey.Entity.DeliveryEntity;
import com.IT342.DeliverYey.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@CrossOrigin(origins = "http://localhost:5173")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/insertDelivery")
    public DeliveryEntity insertDelivery(@RequestBody DeliveryEntity delivery) {
        return deliveryService.insertDelivery(delivery);
    }

    @GetMapping("/getAllDelivery")
    public List<DeliveryEntity> getAllDelivery(){
        return deliveryService.getAllDelivery();
    }

    @PutMapping("/updateDelivery")
    public DeliveryEntity updateDelivery(@RequestParam Long deliveryId, @RequestBody DeliveryEntity newDeliveryDetails){
        return deliveryService.updateDelivery(deliveryId, newDeliveryDetails);
    }

    @DeleteMapping("/deleteDelivery/{deliveryId}")
    public String deleteDelivery(@PathVariable Long deliveryId){
        return deliveryService.deleteDelivery(deliveryId);
    }
}
