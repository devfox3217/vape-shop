package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.Setting;
import com.devfox.bbvape.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class PageController {

    private final SettingService settingService;

    @ModelAttribute
    private void commonAttributes(
            Model model
    ) {
        Setting setting = settingService.loadSetting();

        model.addAttribute("setting", setting);
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

    @RequestMapping("/liquid")
    public String liquid() {
        return "liquid";
    }

    @RequestMapping("/liquid/product")
    public String liquidProduct() {
        return "liquid_product";
    }
}
