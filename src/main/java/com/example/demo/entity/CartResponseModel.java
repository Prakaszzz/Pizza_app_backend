package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartResponseModel {

    private String name;

    private Integer price;

    private Integer count;

    private String imgUrl;

}
