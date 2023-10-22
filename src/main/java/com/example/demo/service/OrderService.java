package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.UserCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VarietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
