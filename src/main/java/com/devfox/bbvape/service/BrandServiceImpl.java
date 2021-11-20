package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Brand;
import com.devfox.bbvape.model.Category;
import com.devfox.bbvape.repo.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll(Sort.by(Sort.Direction.ASC, "ord"));
    }

    @Override
    public Code saveBrand(Brand brand) {
        Code code = new Code();

        Brand brandCheck = brandRepository.save(brand);

        if (brandCheck != null) {
            code.setCode(1);
            code.setMsg(brandCheck.getName() + "이 정상적으로 입력되었습니다.");
        } else {
            code.setCode(2);
            code.setMsg("에러");
        }

        return code;
    }

    @Override
    public Code updateBrand(Brand brand) {
        Code code = new Code();

        Brand loadBrand = brandRepository.getById(brand.getId());

        loadBrand.setName(brand.getName());
        loadBrand.setOrd(brand.getOrd());

        Brand brandCheck = brandRepository.save(loadBrand);

        if (brandCheck != null) {
            code.setCode(1);
            code.setMsg(brandCheck.getName() + "이 정상적으로 수정되었습니다.");
        } else {
            code.setCode(2);
            code.setMsg("에러");
        }

        return code;
    }

    @Override
    public void deleteBrand(int id) {
        brandRepository.deleteById(id);
    }
}
