package com.project.taskmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    private Integer id;
    private String title;
    private String description;
    private Date deadline;
    private boolean completed;

}
