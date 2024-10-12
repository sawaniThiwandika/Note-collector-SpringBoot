package lk.ijse.demo.dto.impl;


import lk.ijse.demo.dto.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class NoteDto implements NoteStatus {
    private String noteId;
    private String noteTitle;
    private String noteDescription;
    private String createDate;
    private  String priorityLevel;
    private String userId;
}
