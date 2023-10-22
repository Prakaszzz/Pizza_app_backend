package com.example.demo.service;

import com.example.demo.entity.Menu;
import com.example.demo.repository.MenuRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    MenuRepository menuRepository;


    public ResponseEntity addMenu(List<Menu> menus) {
        for (Menu menu:menus){
           Menu menu1=new Menu();
           menu1.setName(menu.getName());
           menu1.setImgUrl(menu.getImgUrl());
           menu1.setOrderNo(menu.getOrderNo());
            menuRepository.save(menu);
        }
        return new ResponseEntity("Menus Added",HttpStatus.OK);
    }

    public ResponseEntity getAllMenus() {
        List<Menu> menus=menuRepository.findAllByOrderByOrderNo();
        List<Menu> row1= menus.stream().filter(o->o.getRow().equals(1)).collect(Collectors.toList());
        List<Menu> row2= menus.stream().filter(o->o.getRow().equals(2)).collect(Collectors.toList());
        List<Menu> row3= menus.stream().filter(o->o.getRow().equals(3)).collect(Collectors.toList());

        JSONObject res =new JSONObject();
        JSONObject data=new JSONObject();
        res.put("result",data);
        data.put("row1",row1);
        data.put("row2",row2);
        data.put("row3",row3);

        return new ResponseEntity(res,HttpStatus.OK);
    }
}
