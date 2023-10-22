package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    public ResponseEntity<?> validateUser(String name) {
        User user =userRepository.findByUserName(name);
        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }


    public ResponseEntity<?> createUser(User user) {
        if(user.getUserName()!=null && user.getPhoneNumber()!=null){
           User user1=userRepository.findByUserName(user.getUserName());
           if(user1==null){
               User user2=userRepository.findByPhoneNumber(user.getPhoneNumber());
               if(user2==null){
                   User userNew=new User();
                   userNew.setUserName(user.getUserName());
                   userNew.setPhoneNumber(user.getPhoneNumber());
                   userRepository.save(userNew);
                   return new ResponseEntity<>(userNew,HttpStatus.OK);
               }
               else{
                   return new ResponseEntity<>("Phone number already registered",HttpStatus.OK);
               }
           }
           else{
               return new ResponseEntity<>("UserName already taken",HttpStatus.OK);
           }
        }
        else{
            return new ResponseEntity<>("UserName or phone number not provided",HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getUser(String name) {
        if(name!=null){
            User user=userRepository.findByUserName(name);
            if (user!=null){
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>("Name not provided",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> addAddress(Address address) {
        if(address.getUser()!=null && address.getUser().getId()!=null){
            if(address.getArea()!=null && address.getStreet()!=null && address.getHouseNo()!=null){
                Optional<User> user=userRepository.findById(address.getUser().getId());
                        if(user.isPresent()){
                            Address address1=new Address();
                            address1.setUser(address.getUser());
                            address1.setHouseNo(address.getHouseNo());
                            address1.setArea(address.getArea());
                            address1.setStreet(address.getStreet());
                            addressRepository.save(address1);
                            return new ResponseEntity<>(address,HttpStatus.OK);
                        }
                        else {
                            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
                        }
            }
            else {
                return new ResponseEntity<>("Provide All the Required Details",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("User Not Provided",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAddress(String userId) {
        if(userId!=null) {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                List<Address> addresses=addressRepository.findByUser(user.get());
                if(!addresses.isEmpty()){
                    List<Address> first=new ArrayList<>();
                    List<Address> second=new ArrayList<>();
                    if(addresses.size()>2) {
                        first = addresses.subList(0, 2);
                        second = addresses.subList(2, addresses.size());
                    }
                    else{
                        first=addresses;
                    }
                    JSONObject object=new JSONObject();
                    object.put("first",first);
                    object.put("second",second);
                    return  new ResponseEntity<>(object,HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("No Address",HttpStatus.NO_CONTENT);
                }
            }
            else{
                return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("User Not Provided",HttpStatus.BAD_REQUEST);
    }
}

