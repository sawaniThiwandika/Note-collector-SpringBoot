package lk.ijse.demo.service;



import lk.ijse.demo.dto.impl.NoteDto;

import java.util.List;

public interface NoteService {
    public void saveNote(NoteDto dto);
    public void updateNote(String noteId,NoteDto dto);
    public String deleteNote(String id);
    public NoteDto getNote(String id);
    public List<NoteDto> getNoteList(List<String>idList);

}
