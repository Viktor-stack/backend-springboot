package ru.javabegin.tasklist.backendspringboot.controller;


import org.aspectj.weaver.ArrayReferenceType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepos;

import java.util.List;

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
    public List<CategoryEntity> test() {
        return categoryRepos.findAll();
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

}
