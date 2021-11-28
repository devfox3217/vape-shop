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
    private int id;

    @Column(name = "s_name")
    private String siteName;

    @Column(name = "s_title")
    private String siteTitle;

    @Column(name = "s_pgnum")
    private int sitePageNum;

    @Column(name = "s_point_signup")
    private int sitePointSignup;

    @Column(name = "f_name")
    private String founderName;

    @Column(name = "f_owner")
    private String founderOwner;

    @Column(name = "f_tel")
    private String founderTel;

    @Column(name = "f_email")
    private String founderEmail;

    @Column(name = "f_address")
    private String founderAddress;

    @Column(name = "f_regnum")
    private String founderRegNum;

    @Column(name = "f_e_regnum")
    private String founderERegNum;

    @Column(name = "meta_authors")
    private String metaAuthor;

    @Column(name = "meta_description")
    private String metaDescription;

    @Column(name = "meta_keywords")
    private String metaKeywords;

    @Column(name = "meta_og_title")
    private String metaOgTitle;

    @Column(name = "meta_og_type")
    private String metaOgType;

    @Column(name = "meta_og_image")
    private String metaOgImage;

    @Column(name = "meta_og_site_name")
    private String metaOgSiteName;

    @Column(name = "meta_og_url")
    private String metaOgUrl;

    @Column(name = "meta_og_description")
    private String metaOgDescription;

    @Column(name = "meta_twitter_card")
    private String metaTwitterCard;

    @Column(name = "meta_twitter_title")
    private String metaTwitterTitle;

    @Column(name = "meta_twitter_url")
    private String metaTwitterUrl;

    @Column(name = "meta_twitter_image")
    private String metaTwitterImage;

    @Column(name = "meta_twitter_description")
    private String metaTwitterDescription;

    @Column(name = "g_gtag")
    private String gTag;
}
