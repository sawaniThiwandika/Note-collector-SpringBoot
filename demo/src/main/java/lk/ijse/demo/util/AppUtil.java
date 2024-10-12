package lk.ijse.demo.util;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateNoteID(){
        return "NOTE"+ UUID.randomUUID();
    }
    public static String generateUserID(){
        return "UID"+ UUID.randomUUID();
    }
    public static String convertProfilePictureToBase64(byte[] profilePicture) throws IOException {
        return Base64.getEncoder().encodeToString(profilePicture);
    }

}
