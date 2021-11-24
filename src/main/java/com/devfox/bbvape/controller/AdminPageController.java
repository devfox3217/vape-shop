package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.Brand;
import com.devfox.bbvape.model.Category;
import com.devfox.bbvape.service.BrandService;
import com.devfox.bbvape.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPageController {

    private final CategoryService categoryService;
    private final BrandService brandService;

    @RequestMapping(value = {"", "/", "dashboard"})
    public String dashboard() {

        return "admin/dashboard";
    }

    @RequestMapping("/setting/site")
    public String settingSite() {

        return "admin/setting_site";
    }

    @RequestMapping("/setting/category")
    public String settingCategory(
            Model model
    ) {
        List<Category> categoryList = categoryService.getCategories();

        model.addAttribute(categoryList);

        return "admin/setting_category";
    }

    @RequestMapping("/setting/brand")
    public String settingBrand(
            Model model
    ) {

        List<Brand> brandList = brandService.getBrands();

        model.addAttribute(brandList);

        return "admin/setting_brand";
    }

    @RequestMapping("/product/list")
    public String productList() {

        return "admin/product_list";
    }

    @RequestMapping("/product/insert")
    public String productInsert(
            Model model
    ) {
        List<Brand> brandList = brandService.getBrands();
        model.addAttribute(brandList);

        return "admin/product_insert";
    }

}
