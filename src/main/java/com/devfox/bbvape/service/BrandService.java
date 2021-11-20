package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getBrands();

    Code saveBrand(Brand brand);

    Code updateBrand(Brand brand);

    void deleteBrand(int id);
}
