package ru.javabegin.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskSearchValue {
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

    // переменые для построничности
    private Integer pageNumber;
    private Integer pageSize;

    // переменые для сортеровки
    private String sortColumn;
    private String sortDirection;
}
