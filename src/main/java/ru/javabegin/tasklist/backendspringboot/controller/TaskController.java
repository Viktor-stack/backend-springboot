package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.javabegin.tasklist.backendspringboot.entity.TaskEntity;
import ru.javabegin.tasklist.backendspringboot.repo.TaskRepo;
import ru.javabegin.tasklist.backendspringboot.search.TaskSearchValue;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskRepo taskRepo;

    public TaskController(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskEntity>> findAll() {
        System.out.println("TaskController: findAll() ---------------------------------------------------------------");
        return ResponseEntity.ok(taskRepo.findByOrderByTitleAsc());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        System.out.println("TaskController getTaskByID()-------------------------------------------------------------");
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

    @PostMapping("/search")
    public ResponseEntity<List<TaskEntity>> search(@RequestBody TaskSearchValue taskSearchValue) {
        System.out.println("TaskController search()------------------------------------------------------------------");
        HttpMessageNotReadableException err = null;
        List<TaskEntity> task = null;
        try {
            task =  taskRepo.findByTitle(taskSearchValue.getTitle());
        } catch (HttpMessageNotReadableException e) {
            e.printStackTrace();
            return new ResponseEntity("Не чего не найдено", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("/update")
    public ResponseEntity<TaskEntity> update(@RequestBody TaskEntity taskEntity) {
        System.out.println("TaskController update()------------------------------------------------------------------");
        TaskEntity task;
        try {
            if (taskEntity.getId() == null || taskEntity.getId() == 0) {
                return new ResponseEntity("redundant param: ID MIST be Null", HttpStatus.NOT_ACCEPTABLE);
            }
            if (taskEntity.getTitle() == null || taskEntity.getTitle().trim().length() == 0) {
                return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
            }
            task = taskRepo.save(taskEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

}
