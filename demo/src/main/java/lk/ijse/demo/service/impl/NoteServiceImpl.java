package lk.ijse.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.demo.dao.NoteDao;
import lk.ijse.demo.dto.impl.NoteDto;
import lk.ijse.demo.entity.impl.NoteEntity;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.NoteNotFoundException;
import lk.ijse.demo.service.NoteService;
import lk.ijse.demo.util.AppUtil;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service //meta annotation ekk emeka nisa api aya componenet kyn annotation ek use krnn oni ne, application context ekt wetenw, bean ekk wenw
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteDao noteDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveNote(NoteDto dto) {
        System.out.println("NoteService :"+ dto);
        dto.setNoteId(AppUtil.generateNoteID());
        System.out.println("NoteService :"+ dto);
        NoteEntity save = noteDao.save(mapping.toNoteEntity(dto));
        System.out.println("NoteService :"+ dto);
        System.out.println(save);
        if(dto==null){
            throw new DataPersistException();
        }

    }

    @Override
    public void updateNote(String noteId,NoteDto dto) {
        Optional<NoteEntity> findNote = noteDao.findById(noteId);
        if (!findNote.isPresent()) {
            throw new NoteNotFoundException("Note not found");
        }else {
            findNote.get().setNoteTitle(dto.getNoteTitle());
            findNote.get().setNoteDescription(dto.getNoteDescription());
            findNote.get().setPriorityLevel(dto.getCreateDate());
            findNote.get().setPriorityLevel(dto.getPriorityLevel());
        }
    }

    @Override
    public String deleteNote(String id) {
        Optional<NoteEntity> existNote = noteDao.findById(id);
        if (existNote.isPresent()) {
            noteDao.delete(existNote.get());
        }

        return "Success";

    }

    @Override
    public NoteDto getNote(String id) {
        NoteEntity noteEntity = noteDao.findById(id).get();
        return mapping.toNoteDto(noteEntity);
    }

    @Override
    public List<NoteDto> getNoteList(List<String> idList) {
        return null;
    }
}
