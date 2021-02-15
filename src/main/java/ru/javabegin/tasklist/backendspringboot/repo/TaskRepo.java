package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.TaskEntity;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT p FROM TaskEntity p WHERE" +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title ,'%'))) and" +
            "(:completed is null or p.completed=:completed) and" +
            "(:priorityId is null or p.priority.id=:priorityId) and" +
            "(:categoryId is null or p.category.id=:categoryId)")
    Page<TaskEntity> findByParams(
            @Param("title") String title,
            @Param("completed") Integer completed,
            @Param("priorityId") Long priorityId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    List<TaskEntity> findByOrderByTitleAsc();
}
