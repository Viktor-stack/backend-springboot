package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.TaskEntity;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT t FROM TaskEntity t WHERE" +
            "(:title is null or :title='' or lower(t.title) like lower(concat('%', :title ,'%')))" +
            "order by t.title ASC ")
    List<TaskEntity> findByTitle(@Param("title") String title);

    List<TaskEntity> findByOrderByTitleAsc();
}
