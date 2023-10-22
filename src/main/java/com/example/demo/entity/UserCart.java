package com.example.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private Integer count;

    private Boolean isOrdered;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "variety_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Variety variety;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
