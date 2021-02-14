package ru.javabegin.tasklist.backendspringboot.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepos;
import ru.javabegin.tasklist.backendspringboot.search.CategorySearchValues;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category") // default point
public class CategoryController {

    // Подклбюченик Repository длф use method.findAll() итД!
    private final CategoryRepos categoryRepos;

    // Конструктор клфса
    public CategoryController(CategoryRepos categoryRepos) {
        this.categoryRepos = categoryRepos;
    }

    // End Point GET ==> http://localhost:8080/category/all
    @GetMapping("/all")
    public List<CategoryEntity> findAll() {
        return categoryRepos.findAllByOrderByTitleAsc();
    }

    // End Point POST ==>
    @PostMapping("/add")
    public ResponseEntity<CategoryEntity> add(@RequestBody CategoryEntity category) {

        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id MIST be Null ", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepos.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity upload(@RequestBody CategoryEntity category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("redundant param: ID MIST be Null ", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryRepos.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Long id) {
        CategoryEntity category;
        try {
            category = categoryRepos.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            categoryRepos.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity("Category delete successful " + id, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryEntity>> search(@RequestBody CategorySearchValues categorySearchValues) {
        return ResponseEntity.ok(categoryRepos.findByTitle(categorySearchValues.getText()));
    }

}
