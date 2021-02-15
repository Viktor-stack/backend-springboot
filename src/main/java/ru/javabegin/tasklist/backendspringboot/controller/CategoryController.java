package ru.javabegin.tasklist.backendspringboot.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;
import ru.javabegin.tasklist.backendspringboot.search.CategorySearchValues;
import ru.javabegin.tasklist.backendspringboot.service.CategoryService;
import ru.javabegin.tasklist.backendspringboot.utill.MyLoader;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category") // default point
public class CategoryController {

    // Подклбюченик Repository длф use method.findAll() итД!
    private final CategoryService categoryService;

    // Конструктор клфса Inject Service
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // End Point GET ==> http://localhost:8080/category/all
    @GetMapping("/all")
    public List<CategoryEntity> findAll() {
        MyLoader.printText("CategoryController: findAll()------------------------------------------------------------");
        return categoryService.findAllByOrderByTitleAsc();
    }

    // End Point POST ==> http://localhost:8080/category/add
    @PostMapping("/add")
    public ResponseEntity<CategoryEntity> add(@RequestBody CategoryEntity category) {
        MyLoader.printText("CategoryController: add()------------------------------------------------------------");
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id MIST be Null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity upload(@RequestBody CategoryEntity category) {
        MyLoader.printText("CategoryController: upload()------------------------------------------------------------");
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("redundant param: ID MIST be Null ", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.update(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Long id) {
        MyLoader.printText("CategoryController: findById()------------------------------------------------------------");
        CategoryEntity category;
        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        MyLoader.printText("CategoryController: delete()------------------------------------------------------------");
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity("Category delete successful " + id, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryEntity>> search(@RequestBody CategorySearchValues categorySearchValues) {
        MyLoader.printText("CategoryController: search()------------------------------------------------------------");
        return ResponseEntity.ok(categoryService.findByTitle(categorySearchValues.getTitle()));
    }

}
