package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserCart;
import com.example.demo.entity.Variety;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCartRepository extends JpaRepository<UserCart,String> {

    UserCart findByUserAndVarietyAndIsOrdered(User user, Variety variety, boolean b);

    List<UserCart> findByUserAndIsOrdered(User user, boolean b);
}
