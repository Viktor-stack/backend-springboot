package ru.javabegin.tasklist.backendspringboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@Table(name = "category", schema = "tasklist")
public class CategoryEntity {
    private Long id;
    private String title;
    private Long completedCount;
    private Long uncompletedCount;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = "completed_count")
    public Long getCompletedCount() {
        return completedCount;
    }

    @Basic
    @Column(name = "uncompleted_count")
    public Long getUncompletedCount() {
        return uncompletedCount;
    }

}
