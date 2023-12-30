package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import net.minidev.json.JSONArray;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCartRepository userCartRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    VarietyRepository varietyRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserOrderRepository userOrderRepository;


    public ResponseEntity<?> getCount(String userId, String varietyId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Optional<Variety> variety=varietyRepository.findById(varietyId);
            if(variety.isPresent()){
                UserCart cart=userCartRepository.findByUserAndVarietyAndIsOrdered(user.get(),variety.get(),false);
                if(cart!=null) {
                    return new ResponseEntity<>(cart.getCount(), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(0, HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity<>("variety Not Found",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("User Not Found",HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> addCartProducts(Boolean s, CartRequestModel cartRequestModel) {
        Optional<User> user=userRepository.findById(cartRequestModel.getUserId());
        if (user.isPresent()){
            Optional<Variety> variety=varietyRepository.findById(cartRequestModel.getVarietyId());
            if(variety.isPresent()){
                UserCart userCart=userCartRepository.findByUserAndVarietyAndIsOrdered(user.get(),variety.get(),false);
                if(userCart!=null){
                    if(s){
                        userCart.setCount(userCart.getCount()+1);
                        userCartRepository.save(userCart);
                        return new ResponseEntity<>(userCart,HttpStatus.OK);
                    }
                    else{
                        if(userCart.getCount()==1){
                            userCartRepository.delete(userCart);
                            return new ResponseEntity<>("Cart removed",HttpStatus.OK);
                        }
                        else{
                            userCart.setCount(userCart.getCount()-1);
                            userCartRepository.save(userCart);
                            return new ResponseEntity<>(userCart,HttpStatus.OK);
                        }
                    }
                }
                else{
                    UserCart cart=new UserCart();
                    cart.setUser(user.get());
                    cart.setVariety(variety.get());
                    cart.setCount(1);
                    cart.setIsOrdered(false);
                    userCartRepository.save(cart);
                }
            }
            else{
                return new ResponseEntity<>("variety not found",HttpStatus.BAD_REQUEST);
            }

        }
        else{
            return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public ResponseEntity<?> getCartProducts(String userId) {
        Optional<User> user=userRepository.findById(userId);
    if(user.isPresent()){
        List<UserCart> carts=userCartRepository.findByUserAndIsOrdered(user.get(),false);
        if (!carts.isEmpty()) {
            List<CartResponseModel> cartResponseModels = new ArrayList<>();
            for (UserCart cart : carts) {
                CartResponseModel model = new CartResponseModel();
                model.setName(cart.getVariety().getName());
                model.setPrice(cart.getVariety().getPrice());
                model.setCount(cart.getCount());
                model.setImgUrl(cart.getVariety().getImgUrl());
                cartResponseModels.add(model);
            }
            return new ResponseEntity<>(cartResponseModels, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    else{
        return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
    }
    }

    public ResponseEntity<?> getPreviousOrders(String userId) {
        if (userId!=null){
            Optional<User> user=userRepository.findById(userId);
            if (user.isPresent()){
               List<UserOrder> orders=userOrderRepository.findAll();
               return  new ResponseEntity<>(orders,HttpStatus.OK);
            }
        }
        return  null;
    }

    public ResponseEntity<?> orderProducts(UserOrder order) {
        System.out.print(order);

        if(order.getAddress()!=null && order.getAddress().getId()!=null && order.getUser()!=null && order.getUser().getId()!=null){
            Optional<User> user=userRepository.findById(order.getUser().getId());
            if (user.isPresent()){
                Optional<Address> address =addressRepository.findById(order.getAddress().getId());
                if (address.isPresent()){
                    UserOrder userOrder=new UserOrder();
                    userOrder.setUser(user.get());
                    userOrder.setAddress(address.get());
                    List<UserCart> carts=userCartRepository.findByUserAndIsOrdered(user.get(),false);
                    userOrder.setCarts(carts);
                    Integer price=0;
                    for (UserCart cart:carts){
                        price+=cart.getVariety().getPrice();
                        cart.setIsOrdered(true);
                        userCartRepository.save(cart);
                    }
                    userOrder.setOrderedTime(ZonedDateTime.now());
                    userOrder.setPaymentMethod(order.getPaymentMethod());
                    userOrder.setTotalPrice(price);
                    UserOrder order1=userOrderRepository.save(userOrder);
                  return  new ResponseEntity<>(order1,HttpStatus.OK);

                }
            }
        }
        else {
            return  new ResponseEntity<>("Provide all the required fields",HttpStatus.BAD_REQUEST);
        }
        return  null;
    }
}
