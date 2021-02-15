package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.tasklist.backendspringboot.entity.StatEntity;
import ru.javabegin.tasklist.backendspringboot.repo.StatRepos;
import ru.javabegin.tasklist.backendspringboot.utill.MyLoader;

@RestController
public class StatController {

    private final StatRepos statRepos;

    public StatController(StatRepos statRepos) {
        this.statRepos = statRepos;
    }

    @GetMapping("/stat")
    public ResponseEntity<StatEntity> findById() {
        MyLoader.printText("StatController: findById()---------------------------------------------------------------");
        Long defaultID = 1L;
        return ResponseEntity.ok(statRepos.findById(defaultID).get());
    }

}
