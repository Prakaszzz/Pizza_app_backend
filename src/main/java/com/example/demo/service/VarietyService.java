package com.example.demo.service;

import com.example.demo.entity.Menu;
import com.example.demo.entity.Variety;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.VarietyRepository;
import net.minidev.json.JSONObject;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VarietyService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    VarietyRepository varietyRepository;
    public ResponseEntity<?> addVariety(String id, List<Variety> varieties) {
        Optional<Menu> menu=menuRepository.findById(id);
        Integer order=0;
        if(menu.isPresent()) {
            if (!varieties.isEmpty()) {
                for (Variety variety:varieties){
                    order++;
                    Variety variety1=new Variety();
                    variety1.setOrderNo(order);
                    variety1.setName(variety.getName());
                    variety1.setImgUrl(variety.getImgUrl());
                    variety1.setMenu(menu.get());
                    varietyRepository.save(variety1);
                }
            }
        }
        return new ResponseEntity<>("saved successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> getVarietyList(String menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if(menu.isPresent()){
        List<Variety> varieties = varietyRepository.findByMenu(menu.get());
        if (!varieties.isEmpty()) {
            int n = varieties.size() / 2;
            List<Variety> first = varieties.subList(0, n);
            List<Variety> second = varieties.subList(n, varieties.size());
            JSONObject object = new JSONObject();
            object.put("first", first);
            object.put("second", second);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
        else {
            return new ResponseEntity<>("Menu Not Found",HttpStatus.BAD_REQUEST);
        }
    }
}
