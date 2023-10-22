package com.example.demo.repository;

import com.example.demo.entity.Menu;
import com.example.demo.entity.Variety;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VarietyRepository  extends JpaRepository<Variety,String> {

    List<Variety> findByMenu(Menu menu);
}
