package ru.javabegin.tasklist.backendspringboot.service;

import org.springframework.stereotype.Service;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepos;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepos categoryRepos;

    public CategoryService(CategoryRepos categoryRepos) {
        this.categoryRepos = categoryRepos;
    }

    public List<CategoryEntity> findAll() {
        return categoryRepos.findAll();
    }

    public CategoryEntity add(CategoryEntity categoryEntity) {
        return categoryRepos.save(categoryEntity);
    }

    public CategoryEntity update(CategoryEntity categoryEntity) {
        return categoryRepos.save(categoryEntity);
    }

    public void deleteById(Long id) {
        categoryRepos.deleteById(id);
    }

    public List<CategoryEntity> findByTitle(String title) {
        return categoryRepos.findByTitle(title);
    }

    public CategoryEntity findById(Long id) {
        return categoryRepos.findById(id).get();
    }

    public List<CategoryEntity> findAllByOrderByTitleAsc() {
        return categoryRepos.findAllByOrderByTitleAsc();
    }

}
