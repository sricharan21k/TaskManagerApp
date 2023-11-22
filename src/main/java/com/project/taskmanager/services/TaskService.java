package com.project.taskmanager.services;

import com.project.taskmanager.entities.TaskEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    private Integer taskId = 1;

    private final SimpleDateFormat deadlineFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<TaskEntity> tasks = new ArrayList<>();

    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineFormat.parse(deadline));
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public TaskEntity getTask(int taskId) {
        return tasks.stream()
                .filter(task -> task.getId() == taskId).findAny().orElse(null);
    }

    public List<TaskEntity> getAllTasks() {
        return tasks;
    }

    public TaskEntity updateTask(int taskId, String description, String deadline, Boolean completed) throws ParseException {
        TaskEntity task = getTask(taskId);
        if (task == null) return null;
        if (description != null) task.setDescription(description);
        if (deadline != null) task.setDeadline(deadlineFormat.parse(deadline));
        if (completed != null) task.setCompleted(completed);
        return task;
    }

    public boolean deleteTask(int taskId){
        return tasks.remove(getTask(taskId));
    }
}
