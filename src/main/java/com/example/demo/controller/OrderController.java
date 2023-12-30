package com.example.demo.controller;

import com.example.demo.entity.CartRequestModel;
import com.example.demo.entity.UserOrder;
import com.example.demo.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/res/v1/")
public class OrderController
{
    @Autowired
    OrderService orderService;

   @GetMapping("user/{userId}/variety/{varietyId}/count")
    public ResponseEntity<?> getCountOfCart(@PathVariable String userId,@PathVariable String varietyId){
        return orderService.getCount(userId,varietyId);
    }

    @PutMapping("/cart/product")
    public ResponseEntity<?> addCartProducts(@RequestParam Boolean s, @RequestBody CartRequestModel cartRequestModel){
       return orderService. addCartProducts(s,cartRequestModel);
    }

    @GetMapping("user/{userId}/cart/products")
    public ResponseEntity<?> getCartProducts(@PathVariable String userId){
       return orderService.getCartProducts(userId);
    }

    @GetMapping("user/{userId}/orders")
    public  ResponseEntity<?> getPreviousOrders(@PathVariable String userId){
       return  orderService.getPreviousOrders(userId);
    }

   @PostMapping("/user/order/products")
    public ResponseEntity<?> orderProducts(@RequestBody UserOrder order){
       return  orderService.orderProducts(order);
   }

}
