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
    private final ProductService productService;
    private final ProductImgService productImgService;
    private final ProductCategoryService productCategoryService;

    // ******************** 상품 카테고리 페이지 ********************

    @PostMapping(value = "/getCategoryRef")
    @ResponseBody
    public List<Category> getCategoryRef(@RequestBody Map<String, String> data) {
        List<Category> categoryList = categoryService.getHighCategory(data.get("type"));
        return categoryList;
    }

    @PostMapping(value = "getCategories")
    @ResponseBody
    public List<Category> getCategories() {
        List<Category> categoryList = categoryService.getCategories();
        return categoryList;
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

    // ******************** 상품등록 페이지 ********************

    @RequestMapping("/productInsert")
    public void productInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam int brand,
            @RequestParam String name,
            @RequestParam String spec,
            @RequestParam int cost_price,
            @RequestParam(required = false) int discount_price,
            @RequestParam int remain,
            @RequestParam int[] category,
            @RequestParam List<MultipartFile> img,
            @RequestParam MultipartFile thumbnail
    ) throws IOException {
        String imgLocation = "/var/boot/bbvape/upload/img";
        String thumbnailLocation = "/var/boot/bbvape/upload/thumbnail";

        // 상품(Product)에 넣기
        Product product = new Product();
        product.setType(type);
        product.setBrand(brand);
        product.setName(name);
        product.setSpec(spec);
        product.setCostPrice(cost_price);
        product.setDiscountPrice(discount_price);
        product.setRemain(remain);
        product.setEnabled(true);
        product.setSoldOut(false);

        // 썸네일 업로드
        String thumbnailName = "thumbnail" + FileNameUtil.getFileName(thumbnail.getOriginalFilename());
        File thumbnailFile = new File(thumbnailLocation, thumbnailName);

        product.setThumbnail(thumbnailName);

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (Exception e) {
            PageScriptUtil.alertAndBack(response, "잘못된 업로드입니다.");
        }

        Product saveProduct = productService.createProduct(product);

        if (saveProduct != null) {

            for (int j : category) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductId(saveProduct.getId());
                productCategory.setCategoryId(j);
                productCategory.setBadge(false);

                productCategoryService.createProductCategory(productCategory);

            }

            // 상세이미지 업로드
            int imgIndex = 0;
            for (MultipartFile multipartFile : img) {
                String imgName = "img" + imgIndex + FileNameUtil.getFileName(multipartFile.getOriginalFilename());

                File imgfile = new File(imgLocation, imgName);

                try {
                    multipartFile.transferTo(imgfile);
                    ProductImg productImg = new ProductImg();
                    productImg.setImg(imgName);
                    productImg.setOrd(imgIndex);
                    productImg.setProductId(saveProduct.getId());

                    productImgService.createProductImg(productImg);

                } catch (Exception e) {
                    PageScriptUtil.alertAndBack(response, "상세이미지가 제대로 입력되지 않았습니다.");
                }
                imgIndex = imgIndex + 1;
            }

        } else {
            PageScriptUtil.alertAndBack(response, "잘못된 업로드입니다.");
        }

        PageScriptUtil.alertAndMove(response, "상품이 입력되었습니다.", "/admin/product/insert");
    }

}
