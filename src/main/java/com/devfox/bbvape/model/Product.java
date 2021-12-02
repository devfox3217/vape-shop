package com.devfox.bbvape.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
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

    @Column(name = "subname")
    private String subname;

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

    @Column(name = "content")
    private String content;

    @Column(name = "content_edit")
    private String contentEdit;

    @ManyToOne
    @JoinColumn(name = "brand", insertable = false, updatable = false)
    private Brand brandItem;


}
