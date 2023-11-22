package com.project.taskmanager.controllers;

import com.project.taskmanager.dto.CreateNoteDTO;
import com.project.taskmanager.dto.CreateNoteResponseDTO;
import com.project.taskmanager.dto.UpdateNoteDTO;
import com.project.taskmanager.entities.NoteEntity;
import com.project.taskmanager.services.NotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") int taskId) {
        var notes = notesService.getNotes(taskId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<CreateNoteResponseDTO> addNote(
            @PathVariable("taskId") int taskId,
            @RequestBody CreateNoteDTO body) {
        var note = notesService.addNoteForTask(taskId, body.getTitle(), body.getBody());
        return ResponseEntity.ok(new CreateNoteResponseDTO(taskId, note));
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<NoteEntity> updateNote(
            @PathVariable("taskId") int taskId,
            @PathVariable int noteId,
            @RequestBody UpdateNoteDTO body) {

        var note = notesService.updateNote(taskId, noteId, body.getTitle(), body.getBody());
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable("taskId") int taskId, @PathVariable int noteId) {
        return notesService.deleteNote(taskId, noteId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
