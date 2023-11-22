package com.project.taskmanager.dto;

import com.project.taskmanager.entities.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteResponseDTO {
    private Integer taskId;
    private NoteEntity note;
}
