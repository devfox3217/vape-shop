package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.*;
import com.devfox.bbvape.service.*;
import com.devfox.bbvape.util.FileNameUtil;
import com.devfox.bbvape.util.PageScriptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/admin/setting")
public class AdminSettingController {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SettingService settingService;

    // ******************** 사이트 세팅 페이지 ********************

    @RequestMapping("/settingInsert")
    public void settingInsert(
            HttpServletResponse response,
            @RequestParam String s_name,
            @RequestParam String s_title,
            @RequestParam int s_pgnum,
            @RequestParam int s_point_signup,
            @RequestParam String f_name,
            @RequestParam String f_owner,
            @RequestParam String f_tel,
            @RequestParam String f_email,
            @RequestParam String f_address,
            @RequestParam String f_regnum,
            @RequestParam String f_e_regnum,
            @RequestParam String meta_authors,
            @RequestParam String meta_description,
            @RequestParam String meta_keywords,
            @RequestParam String meta_og_title,
            @RequestParam String meta_og_type,
            @RequestParam MultipartFile meta_og_image,
            @RequestParam String meta_og_site_name,
            @RequestParam String meta_og_url,
            @RequestParam String meta_og_description,
            @RequestParam String meta_twitter_card,
            @RequestParam String meta_twitter_title,
            @RequestParam String meta_twitter_url,
            @RequestParam MultipartFile meta_twitter_image,
            @RequestParam String meta_twitter_description,
            @RequestParam String g_gtag
    ) throws IOException {
        Setting setting = new Setting();
        Setting originalSetting = settingService.loadSetting();
        String settingImageLocation = "/var/boot/bbvape/upload/setting";

        setting.setSiteName(s_name);
        setting.setSiteTitle(s_title);
        setting.setSitePageNum(s_pgnum);
        setting.setSitePointSignup(s_point_signup);
        setting.setFounderName(f_name);
        setting.setFounderOwner(f_owner);
        setting.setFounderTel(f_tel);
        setting.setFounderEmail(f_email);
        setting.setFounderAddress(f_address);
        setting.setFounderRegNum(f_regnum);
        setting.setFounderERegNum(f_e_regnum);
        setting.setMetaAuthor(meta_authors);
        setting.setMetaDescription(meta_description);
        setting.setMetaKeywords(meta_keywords);
        setting.setMetaOgTitle(meta_og_title);
        setting.setMetaOgType(meta_og_type);
        setting.setMetaOgSiteName(meta_og_site_name);
        setting.setMetaOgUrl(meta_og_url);
        setting.setMetaOgDescription(meta_og_description);
        setting.setMetaTwitterCard(meta_twitter_card);
        setting.setMetaTwitterTitle(meta_twitter_title);
        setting.setMetaTwitterUrl(meta_twitter_url);
        setting.setMetaTwitterDescription(meta_twitter_description);
        setting.setGTag(g_gtag);

        if (meta_og_image.isEmpty()) {

            setting.setMetaOgImage(originalSetting.getMetaOgImage());
        } else {
            try {
                String metaOgImageName = "meta_og_image" + FileNameUtil.getFileName(meta_og_image.getOriginalFilename());
                File metaOgImageFile = new File(settingImageLocation, metaOgImageName);
                meta_og_image.transferTo(metaOgImageFile);
                setting.setMetaOgImage(metaOgImageName);
            } catch (Exception e) {
                PageScriptUtil.alertAndBack(response, "잘못된 업로드입니다.");
            }
        }

        if (meta_twitter_image.isEmpty()) {
            setting.setMetaTwitterImage(originalSetting.getMetaTwitterImage());
        } else {
            try {
                String metaTwitterImageName = "meta_twitter_image" + FileNameUtil.getFileName(meta_twitter_image.getOriginalFilename());
                File metaTwitterImageFile = new File(settingImageLocation, metaTwitterImageName);
                meta_twitter_image.transferTo(metaTwitterImageFile);
                setting.setMetaTwitterImage(metaTwitterImageName);
            } catch (Exception e) {
                PageScriptUtil.alertAndBack(response, "잘못된 업로드입니다.");
            }
        }

        settingService.saveSetting(setting);
        PageScriptUtil.alertAndMove(response, "설정이 저장되었습니다", "/admin/setting/site");
    }

    // ******************** 상품 카테고리 페이지 ********************

    @PostMapping(value = "/getCategoryRef")
    @ResponseBody
    public List<Category> getCategoryRef(@RequestBody Map<String, String> data) {
        return categoryService.getHighCategory(data.get("type"));
    }

    @PostMapping(value = "getCategories")
    @ResponseBody
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @RequestMapping("/categoryInsert")
    public void categoryInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int ref,
            @RequestParam(required = false, defaultValue = "0") int ord
    ) throws IOException {
        Category category = new Category();

        category.setType(type);
        category.setOrd(ord);
        category.setName(name);
        category.setRef(ref);

        Code code = categoryService.saveCategory(category);

        PageScriptUtil.alertAndMove(response, code.getMsg(), "/admin/setting/category");

    }

    @RequestMapping("/categoryUpdate")
    public void categoryUpdate(
            HttpServletResponse response,
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int ord
    ) throws IOException {
        Category category = new Category();

        category.setId(id);
        category.setName(name);
        category.setOrd(ord);

        Code code = categoryService.updateCategory(category);

        PageScriptUtil.alertAndMove(response, code.getMsg(), "/admin/setting/category");

    }

    @RequestMapping("/categoryDelete")
    public void categoryDelete(
            HttpServletResponse response,
            @RequestParam int id
    ) throws IOException {
        categoryService.deleteCategory(id);

        PageScriptUtil.alertAndMove(response, "삭제가 완료되었습니다.", "/admin/setting/category");
    }

    // ******************** 제조사 페이지 ********************

    @PostMapping(value = "/getBrands")
    @ResponseBody
    public List<Brand> getBrand(@RequestBody Map<String, String> data) {
        return brandService.getBrands();
    }

    @RequestMapping("/brandInsert")
    public void brandInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int ord
    ) throws IOException {
        Brand brand = new Brand();

        brand.setType(type);
        brand.setOrd(ord);
        brand.setName(name);

        Code code = brandService.saveBrand(brand);

        PageScriptUtil.alertAndMove(response, code.getMsg(), "/admin/setting/brand");

    }

    @RequestMapping("/brandUpdate")
    public void brandUpdate(
            HttpServletResponse response,
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "0") int ord
    ) throws IOException {
        Brand brand = new Brand();

        brand.setId(id);
        brand.setName(name);
        brand.setOrd(ord);

        Code code = brandService.updateBrand(brand);

        PageScriptUtil.alertAndMove(response, code.getMsg(), "/admin/setting/brand");

    }

    @RequestMapping("/brandDelete")
    public void brandDelete(
            HttpServletResponse response,
            @RequestParam int id
    ) throws IOException {
        brandService.deleteBrand(id);

        PageScriptUtil.alertAndMove(response, "삭제가 완료되었습니다.", "/admin/setting/brand");
    }

}
