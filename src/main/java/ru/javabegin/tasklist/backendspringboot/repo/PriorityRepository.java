package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.PriorityEntity;

//принцып ООП: апстракцыя риолизацыя - здесь описаны все доступные способы к даным
@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Long> {
}
