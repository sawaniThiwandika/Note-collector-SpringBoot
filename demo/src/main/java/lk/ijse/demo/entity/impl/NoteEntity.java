package lk.ijse.demo.entity.impl;

import jakarta.persistence.*;
import lk.ijse.demo.entity.SuperEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name="note")
public class NoteEntity implements SuperEntity {
    @Id
    private String noteId;
    private String noteTitle;
    private String noteDescription;
    private String createDate;
    private  String priorityLevel;
    @ManyToOne
    @JoinColumn(name="userId",nullable = false)
    private UserEntity user;
}
