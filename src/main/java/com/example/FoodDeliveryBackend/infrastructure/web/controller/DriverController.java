package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class DriverController {

    @PostMapping("/driver/online")
    public Void PostOnlineDriver(){return null;}

    @PostMapping("/driver/offline")
    public Void PostOfflineDriver(){return null;}

    @PostMapping("/driver/location")
    public Void PostDriverLocation(){return null;}

    @GetMapping("/drivers/deliveries/assigned")
    public Void GetAssignedDrivers(){return null;}

    @PostMapping("/deliveries/{id}/accept")
    public Void AcceptDelivery(){return null;}

    @PostMapping("/deliveries/{id}/picked-up")
    public Void PickedUpDelivery(){return null;}

    @PostMapping("/deliveries/{id}/delivered")
    public Void DeliveredDelivery(){return null;}


}
