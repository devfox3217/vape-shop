package com.devfox.bbvape;

import com.devfox.bbvape.repo.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BbvapeApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

}
