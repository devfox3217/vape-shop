package com.devfox.bbvape.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "brand")
    private int brand;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "spec")
    private String spec;

    @Column(name = "cost_price")
    private int costPrice;

    @Column(name = "discount_price")
    private int discountPrice;

    @Column(name = "remain")
    private int remain;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "soldout")
    private boolean soldOut;

}
