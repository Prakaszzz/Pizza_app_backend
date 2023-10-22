package com.example.demo.controller;

import com.example.demo.entity.Menu;
import com.example.demo.entity.Variety;
import com.example.demo.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/res/v1/")
public class VarietyController {

    @Autowired
    VarietyService varietyService;

    @PostMapping("/variety/menu/{id}")
    public ResponseEntity addVariety(@PathVariable String id, @RequestBody List<Variety> varieties){
       return varietyService.addVariety(id,varieties);
    }


    @GetMapping("/variety/list/menu/{menuId}")
    public ResponseEntity getVarietyList(@PathVariable String menuId){
        return varietyService.getVarietyList(menuId);
    }
}

