package lk.ijse.demo.customStatusCodes;

import lk.ijse.demo.dto.NoteStatus;
import lk.ijse.demo.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedUserErrorStatus implements UserStatus, NoteStatus {
    private int statusCode;
    private String statusMessage;
}
