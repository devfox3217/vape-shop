package com.devfox.bbvape.service;

import com.devfox.bbvape.model.ProductImg;
import com.devfox.bbvape.repo.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgServiceImpl implements ProductImgService {

    private final ProductImgRepository productImgRepository;

    @Override
    public ProductImg createProductImg(ProductImg productImg) {
        return productImgRepository.save(productImg);
    }
}
