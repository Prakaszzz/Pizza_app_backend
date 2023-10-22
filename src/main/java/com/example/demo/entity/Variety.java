package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "variety")
@Data
@NoArgsConstructor
public class Variety {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;

    private Integer orderNo;

    private String imgUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "menu_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Menu menu;

    @OneToMany(mappedBy = "variety", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<UserCart> userCarts;

    private Integer price;
}
