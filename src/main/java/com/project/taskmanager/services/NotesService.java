package com.project.taskmanager.services;

import com.project.taskmanager.entities.NoteEntity;
import com.project.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotesService {
    private final TaskService taskService;

    private final Map<Integer, TaskNotesHolder> taskNotesHolders = new HashMap<>();

    public NotesService(TaskService taskService) {
        this.taskService = taskService;
    }

    static class TaskNotesHolder {
        protected Integer noteId = 1;
        protected List<NoteEntity> notes = new ArrayList<>();
    }

    public List<NoteEntity> getNotes(int taskId) {
        TaskEntity task = taskService.getTask(taskId);
        if (task == null) return null;

        if (taskNotesHolders.get(taskId) == null) {
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        }
        return taskNotesHolders.get(taskId).notes;
    }

    public NoteEntity getNote(int taskId, int noteId) {

        return taskNotesHolders.get(taskId).notes
                .stream()
                .filter(note -> note.getId() == noteId)
                .findAny()
                .orElse(null);

    }

    public NoteEntity addNoteForTask(int taskId, String title, String body) {
        TaskEntity task = taskService.getTask(taskId);
        if (task == null) return null;

        if (taskNotesHolders.get(taskId) == null) {
            taskNotesHolders.put(taskId, new TaskNotesHolder());
        }

        TaskNotesHolder taskNotesHolder = taskNotesHolders.get(taskId);
        NoteEntity note = new NoteEntity();
        note.setId(taskNotesHolder.noteId);
        note.setTitle(title);
        note.setBody(body);
        taskNotesHolder.notes.add(note);
        taskNotesHolder.noteId++;
        taskNotesHolders.put(taskId, taskNotesHolder);

        return note;
    }

    public NoteEntity updateNote(int taskId, int noteId, String title, String body) {
        TaskEntity task = taskService.getTask(taskId);
        if (task == null) return null;
        NoteEntity note = taskNotesHolders.get(taskId).notes.get(noteId);
        if (note == null) {
            return addNoteForTask(taskId, title, body);
        }
        note.setTitle(title);
        note.setBody(body);

        return note;
    }

    public boolean deleteNote(int taskId, int noteId) {
        if (taskService.getTask(taskId) == null) return false;
        NoteEntity note = getNote(taskId, noteId);
        if (note == null) return false;

        return taskNotesHolders.get(taskId).notes.remove(note);
    }

}
