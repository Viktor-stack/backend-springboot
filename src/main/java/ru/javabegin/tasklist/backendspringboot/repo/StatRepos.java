package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.StatEntity;

@Repository
public interface StatRepos extends JpaRepository<StatEntity, Long> {

}
