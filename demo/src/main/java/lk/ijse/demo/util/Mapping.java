package lk.ijse.demo.util;

import lk.ijse.demo.dto.impl.NoteDto;
import lk.ijse.demo.dto.impl.UserDTO;
import lk.ijse.demo.entity.impl.NoteEntity;
import lk.ijse.demo.entity.impl.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;
    public UserEntity toUserEntity (UserDTO userDTO){
        return modelMapper.map(userDTO,UserEntity.class);
    }
    public UserDTO toUserDto (UserEntity userEntity){
        return modelMapper.map(userEntity,UserDTO.class);
    }
    public NoteEntity toNoteEntity (NoteDto noteDTO){
        return modelMapper.map(noteDTO,NoteEntity.class);
    }
    public NoteDto toNoteDto (NoteEntity noteEntity){
        return modelMapper.map(noteEntity,NoteDto.class);
    }
}
