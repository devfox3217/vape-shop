package com.devfox.bbvape.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "SETTING")
@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point_signup")
    private int pointSignup;

    @Column(name = "point_signin")
    private int pointSignin;

    @Column(name = "title")
    private String title;

    @Column(name = "shop_name")
    private String shopName;
}
