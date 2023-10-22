package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;

    private Integer orderNo;

    private String imgUrl;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<Variety> variety;

    private Integer row;

}
