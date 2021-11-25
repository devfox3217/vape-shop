package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.Product;
import com.devfox.bbvape.model.ProductCategory;
import com.devfox.bbvape.model.ProductImg;
import com.devfox.bbvape.service.ProductCategoryService;
import com.devfox.bbvape.service.ProductImgService;
import com.devfox.bbvape.service.ProductService;
import com.devfox.bbvape.util.FileNameUtil;
import com.devfox.bbvape.util.PageScriptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;
    private final ProductImgService productImgService;
    private final ProductCategoryService productCategoryService;

    // ******************** 상품등록 페이지 ********************

    @RequestMapping("/productInsert")
    public void productInsert(
            HttpServletResponse response,
            @RequestParam String type,
            @RequestParam int brand,
            @RequestParam String name,
            @RequestParam(required = false) String subname,
            @RequestParam String spec,
            @RequestParam int cost_price,
            @RequestParam(defaultValue = "0") int discount_price,
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
        product.setSubname(subname);
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
