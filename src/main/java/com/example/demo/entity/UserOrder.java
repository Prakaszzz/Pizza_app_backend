package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "user_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private ZonedDateTime orderedTime;

    private String paymentMethod;

    private  Integer totalPrice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<UserCart> carts;


}
