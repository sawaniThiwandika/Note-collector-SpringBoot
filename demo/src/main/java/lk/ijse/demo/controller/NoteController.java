package lk.ijse.demo.controller;

import lk.ijse.demo.dto.impl.NoteDto;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.NoteNotFoundException;
import lk.ijse.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RestController()
@RequestMapping("api/v1/notes")//v1-version ek
public class NoteController {
    //@PostMapping
    //public String saveNote() {
    @Autowired
    NoteService note;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDto noteDto) {

        try {
            note.saveNote(noteDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteDto getSelectedNote(@PathVariable("noteId") String noteId) {
        NoteDto noteDto = note.getNote(noteId);
        return noteDto;
    }

    @PutMapping(value ="/{noteId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateAllNotes(
           @RequestPart ("noteId") String noteId,
           @RequestPart ("noteTitle") String noteTitle,
           @RequestPart ("noteFDescription") String noteDescription,
           @RequestPart ("createDate") String createDate,
           @RequestPart ("priorityLevel") String priorityLevel,
           @RequestPart ("userId") String userId

    ) {
        var buildNoteDTO = new NoteDto();
        buildNoteDTO.setNoteId(noteId);
        buildNoteDTO.setNoteDescription(noteDescription);
        buildNoteDTO.setNoteTitle(noteTitle);
        buildNoteDTO.setUserId(userId);
        buildNoteDTO.setCreateDate(createDate);
        buildNoteDTO.setPriorityLevel(priorityLevel);
        note.updateNote(noteId,buildNoteDTO);

        String regexForUserID = "^NOTE[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(noteId);
        try {
            if(!regexMatcher.matches() || buildNoteDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            note.updateNote(noteId,buildNoteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
    @DeleteMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAllNotes(@PathVariable("noteId") String noteId) {
       note.deleteNote(noteId);

    }

    public AbstractList<NoteDto> getAllNotes() {
        return new ArrayList<NoteDto>();
    }
}
