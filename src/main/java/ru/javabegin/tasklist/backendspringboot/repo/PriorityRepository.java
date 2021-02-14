package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.PriorityEntity;

import java.util.List;

//принцып ООП: апстракцыя риолизацыя - здесь описаны все доступные способы к даным
@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Long> {

    @Query("SELECT c FROM PriorityEntity c  where" +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title, '%')))" +
            "order by c.title asc")
    List<PriorityEntity> findByTitle(String title);

    List<PriorityEntity> findAllByOrderByIdAsc();
}
