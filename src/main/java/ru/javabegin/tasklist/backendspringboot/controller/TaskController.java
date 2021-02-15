package ru.javabegin.tasklist.backendspringboot.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;
import ru.javabegin.tasklist.backendspringboot.entity.PriorityEntity;
import ru.javabegin.tasklist.backendspringboot.entity.TaskEntity;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepos;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;
import ru.javabegin.tasklist.backendspringboot.repo.TaskRepo;
import ru.javabegin.tasklist.backendspringboot.search.TaskSearchValue;
import ru.javabegin.tasklist.backendspringboot.utill.MyLoader;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepo taskRepo;
    private final PriorityRepository priorityRepository;
    private final CategoryRepos categoryRepos;

    public TaskController(TaskRepo taskRepo, PriorityRepository priorityRepository, CategoryRepos categoryRepos) {
        this.taskRepo = taskRepo;
        this.priorityRepository = priorityRepository;
        this.categoryRepos = categoryRepos;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskEntity>> findAll() {
        MyLoader.printText("TaskController: findAll() ---------------------------------------------------------------");
        return ResponseEntity.ok(taskRepo.findByOrderByTitleAsc());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        MyLoader.printText("TaskController getTaskByID()-------------------------------------------------------------");
        TaskEntity task;
        try {
            if (id == null || id == 0) {
                return new ResponseEntity("ID: is not valid", HttpStatus.NOT_ACCEPTABLE);
            }
            task = taskRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("ID=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }


    @PostMapping("/add")
    public ResponseEntity<TaskEntity> add(@RequestBody TaskEntity taskEntity) {
        if (taskEntity.getId() != null && taskEntity.getId() != 0) {
            return new ResponseEntity("redundant param: id MIST be Null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (taskEntity.getTitle() == null || taskEntity.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        taskEntity.setCompleted(0);
        taskEntity.setDate(new Date());
        PriorityEntity idPriority = priorityRepository.findById(taskEntity.getPriority().getId()).get();
        taskEntity.setPriority(idPriority);
        CategoryEntity idCategory = categoryRepos.findById(taskEntity.getCategory().getId()).get();
        taskEntity.setCategory(idCategory);
        return ResponseEntity.ok(taskRepo.save(taskEntity));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TaskEntity>> search(@RequestBody TaskSearchValue taskSearchValue) {
        MyLoader.printText("TaskController search()------------------------------------------------------------------");
        String text = taskSearchValue.getTitle() != null ? taskSearchValue.getTitle() : null;
        Integer completed = taskSearchValue.getCompleted() != null ? taskSearchValue.getCompleted() : null;
        Long priorityId = taskSearchValue.getPriorityId() != null ? taskSearchValue.getPriorityId() : null;
        Long categoryId = taskSearchValue.getCategoryId() != null ? taskSearchValue.getCategoryId() : null;

        String sortColumn = taskSearchValue.getSortColumn() != null ? taskSearchValue.getSortColumn() : "title";
        String sortDirection = taskSearchValue.getSortDirection() != null ? taskSearchValue.getSortDirection() : null;

        Integer pageNumber = taskSearchValue.getPageNumber() != null ? taskSearchValue.getPageNumber() : null;
        Integer pageSize = taskSearchValue.getPageSize() != null ? taskSearchValue.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        //обект сортеровки
        Sort sort = Sort.by(direction, sortColumn);
        //обект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        // резульнат с построничным выводом
        Page result = taskRepo.findByParams(text, completed, priorityId, categoryId, pageRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<TaskEntity> update(@RequestBody TaskEntity taskEntity) {
        MyLoader.printText("TaskController update()------------------------------------------------------------------");
        try {
            if (taskEntity.getId() == null || taskEntity.getId() == 0) {
                return new ResponseEntity("redundant param: ID MIST be Null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (taskEntity.getTitle() == null || taskEntity.getTitle().trim().length() == 0) {
                return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            taskRepo.save(taskEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            taskRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("ID: " + id + "is not valid", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity("Task: delete successful", HttpStatus.OK);
    }
}
