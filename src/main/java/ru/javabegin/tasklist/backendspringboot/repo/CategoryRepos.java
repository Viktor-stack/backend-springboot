package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.CategoryEntity;

import java.util.List;

//принцып ООП: апстракцыя риолизацыя - здесь описаны все доступные способы к даным
@Repository
public interface CategoryRepos extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c  where" +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title, '%')))" +
            "order by c.title asc")
    List<CategoryEntity> findByTitle(@Param("title") String title);

    List<CategoryEntity> findAllByOrderByTitleAsc();
}
