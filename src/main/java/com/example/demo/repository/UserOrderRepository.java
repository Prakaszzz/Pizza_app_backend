package com.example.demo.repository;

import com.example.demo.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder,String> {

}
