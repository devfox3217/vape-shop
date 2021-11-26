package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Category;
import com.devfox.bbvape.repo.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Code saveCategory(Category category) {
        Code code = new Code();

        Category categoryCheck = categoryRepository.save(category);

        if (categoryCheck != null) {
            code.setCode(1);
            code.setMsg(categoryCheck.getName() + "이 정상적으로 입력되었습니다.");
        } else {
            code.setCode(2);
            code.setMsg("에러");
        }

        return code;
    }

    @Override
    public Code updateCategory(Category category) {
        Code code = new Code();

        Category loadCategory = categoryRepository.getById(category.getId());

        loadCategory.setName(category.getName());
        loadCategory.setOrd(category.getOrd());

        Category categoryCheck = categoryRepository.save(loadCategory);

        if (categoryCheck != null) {
            code.setCode(1);
            code.setMsg(categoryCheck.getName() + "이 정상적으로 수정되었습니다.");
        } else {
            code.setCode(2);
            code.setMsg("에러");
        }

        return code;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "ord"));
    }

    @Override
    public List<Category> getHighCategory(String type) {
        return categoryRepository.findAllByTypeAndRefOrderByOrd(type, 0);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> readCategoryAsType(String type) {
        return categoryRepository.findByType(type, Sort.by(Sort.Direction.ASC, "ord"));
    }
}
