package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @GetMapping("/get-restaurants-in-area")
    public Void GetRestaurantsInArea(){return null;}

    @GetMapping("/restaurants/{Id}/menu")
    public Void GetRestaurantMenu(){return null;}

    @PostMapping("/order")
    public Void PostOrder(){return null;}

    @GetMapping("/order/{Id}")
    public Void GetOrder(){return null;}

    @GetMapping("/order/my")
    public Void GetOrderMy(){return null;}

    @PostMapping("/orders/{id}/cancel")
    public Void CancelOrder(){return null;}
}
