package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant-admin")
public class RestaurantAdminController {

    @PostMapping("/restaurant")
    public Void PostRestaurant(){return null;}

    @PutMapping("/restaurant/{Id}")
    public Void PutRestaurant(){return null;}

    @PostMapping("/restaurants/{Id}/menu-items")
    public Void PostMenuItem(){return null;}

    @PutMapping("/menu-items/{Id}")
    public Void PutMenuItem(){return null;}

    @PatchMapping("/menu-items/{id}/availability")
    public Void PatchMenuItems(){return null;}

    @GetMapping("restaurants/{id}/orders?status=PLACED")
    public Void GetPlacedOrders(){return null;}

    @PostMapping("/orders/{id}/accept")
    public Void AcceptOrder(){return null;}

    @PostMapping("/orders/{id}/reject")
    public Void RejectOrder(){return null;}

    @PostMapping("/orders/{id}/ready")
    public Void ReadyOrder(){return null;}

}
