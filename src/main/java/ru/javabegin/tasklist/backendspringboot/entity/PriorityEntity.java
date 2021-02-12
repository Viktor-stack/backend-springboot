package ru.javabegin.tasklist.backendspringboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "priority", schema = "tasklist")
public class PriorityEntity {
    private Long id;
    private String title;
    private String color;

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
    @Column(name = "color")
    public String getColor() {
        return color;
    }

}
