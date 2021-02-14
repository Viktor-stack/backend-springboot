package ru.javabegin.tasklist.backendspringboot.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.PriorityEntity;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;
import ru.javabegin.tasklist.backendspringboot.search.PrioritySearchValue;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/all")
    public List<PriorityEntity> findAll() {
        return priorityRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<PriorityEntity> add(@RequestBody PriorityEntity priority) {
        // значение ID не должно быть пучтым
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("redundant param: id MIST be Null", HttpStatus.NOT_ACCEPTABLE);
        }
        // если в базу переданф нули && пучстойе значения в строку Color
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: Title", HttpStatus.NOT_ACCEPTABLE);
        }
        // если в базу переданф нули && пучстойе значения в строку Color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody PriorityEntity priority) {
        // значение ID не должно быть пучтым
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("redundant param: id MIST be Null", HttpStatus.NOT_ACCEPTABLE);
        }
        // если в базу переданф нули && пучстойе значения в строку Color
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        // если в базу переданф нули && пучстойе значения в строку Color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PriorityEntity> findById(@PathVariable Long id) {
        PriorityEntity priority;
        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("ID = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("ID = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity("Priority delete successful " + id, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<List<PriorityEntity>> search(@RequestBody PrioritySearchValue prioritySearchValue) {
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValue.getTest()));
    }

}
