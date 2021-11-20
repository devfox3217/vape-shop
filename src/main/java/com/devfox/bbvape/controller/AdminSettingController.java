package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.Brand;
import com.devfox.bbvape.model.Category;
import com.devfox.bbvape.service.BrandService;
import com.devfox.bbvape.service.CategoryService;
import com.devfox.bbvape.service.Code;
import com.devfox.bbvape.util.PageScriptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/admin/setting")
public class AdminSettingController {

    private final CategoryService categoryService;
    private final BrandService brandService;


    // ******************** 상품 카테고리 페이지 ********************

    @PostMapping(value = "/getCategoryRef")
    @ResponseBody
    public List<Category> getCategoryRef(@RequestBody Map<String, String> data) {
        List<Category> categoryList = categoryService.getHighCategory(data.get("type"));
        return categoryList;
    }

    @RequestMapping("/categoryInsert")
    public void categoryInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam(required = false) int ref,
            @RequestParam(required = false) int ord
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
            @RequestParam int ord
    ) throws IOException {
        Category category = new Category();

        category.setId(id);
        category.setName(name);
        category.setOrd(ord);

       Code code =  categoryService.updateCategory(category);

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

    @RequestMapping("/brandInsert")
    public void brandInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam(required = false) int ord
    ) throws IOException {
        Brand brand = new Brand();

        brand.setType(type);
        brand.setOrd(ord);
        brand.setName(name);

        Code code = brandService.saveBrand(brand);

        PageScriptUtil.alertAndMove(response, code.getMsg(), "/admin/setting/brand");

    }
}
