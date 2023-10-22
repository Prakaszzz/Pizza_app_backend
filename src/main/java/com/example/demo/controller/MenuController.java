package com.example.demo.controller;

import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/res/v1/")
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping("/menus")
    public ResponseEntity addMenu(@RequestBody List<Menu> menu){
        return menuService.addMenu(menu);
    }

    @GetMapping("/menus")
    public ResponseEntity getMenus(){
        return menuService.getAllMenus();
    }

}

