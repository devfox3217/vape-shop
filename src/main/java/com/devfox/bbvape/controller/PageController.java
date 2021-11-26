package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.Category;
import com.devfox.bbvape.model.Product;
import com.devfox.bbvape.model.Setting;
import com.devfox.bbvape.service.CategoryService;
import com.devfox.bbvape.service.ProductService;
import com.devfox.bbvape.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class PageController {

    private final SettingService settingService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @ModelAttribute
    private void commonAttributes(
            Model model
    ) {
        Setting setting = settingService.loadSetting();

        model.addAttribute(setting);
    }

    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = {"/", "index", "home"})
    public String home() {
        return "home";
    }

    @RequestMapping("/product")
    public String product(
            Model model,
            @RequestParam(required = false) String type
    ) {
        if (type.equals("liquid") || type.equals("device") || type.equals("etc")) {

            List<Product> productList = productService.readProductsAsType(type);

            model.addAttribute(productList);
        } else {

            type = "liquid";

            List<Product> productList = productService.readProductsAsType(type);

            model.addAttribute(productList);

        }

        List<Category> categoryList = categoryService.readCategoryAsType(type);

        model.addAttribute(categoryList);

        return "product";
    }

    @RequestMapping("/product/item")
    public String item() {
        return "item";
    }
}
