package lk.ijse.demo.controller;
import lk.ijse.demo.customStatusCodes.SelectedUserErrorStatus;
import lk.ijse.demo.dto.SuperDTO;
import lk.ijse.demo.dto.impl.UserDTO;
import lk.ijse.demo.exception.DataPersistException;
import lk.ijse.demo.exception.UserNotFoundException;
import lk.ijse.demo.service.UserServise;
import lk.ijse.demo.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserServise user;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser( @RequestPart("userFirstName") String userFirstName,
                             @RequestPart("userLastName") String userLastName,
                             @RequestPart("userEmail")String userEmail,
                             @RequestPart("userPassword")String userPassword,
                             @RequestPart("profilePicture") MultipartFile profilePicture) {


        String base64ProPic;
        try {
            byte[] bytesProImg = profilePicture.getBytes();// create byte collection of image meka hdnne ape pahasuwata mok
            //profile picture convert to base64
            base64ProPic = AppUtil.convertProfilePictureToBase64(bytesProImg);//apita byte array ekk database eke save krnn beri nisa eka string ekk krl eka database ekt pass krnw
            //UserId generate
            String userId = AppUtil.generateUserID();
            // Todo: Build the object

            var buildUserDTO = new UserDTO();

            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(userFirstName);
            buildUserDTO.setLastName(userLastName);
            buildUserDTO.setEmail(userEmail);
            buildUserDTO.setPassword(userPassword);
            buildUserDTO.setProfilePic(base64ProPic);
            user.saveUser(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
            // return user.saveUser(buildUserDTO);
        } catch (DataPersistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public SuperDTO getSelectedUser(@PathVariable("userId")String userId){
        UserDTO userDTO1 = user.getUser(userId);
        System.out.println("user "+ userDTO1);

        String regexForUserID = "^UID[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";


       Pattern regexPattern = Pattern.compile(regexForUserID);
       var regexMatcher = regexPattern.matcher(userId);

        if(!regexMatcher.matches()){
            return new SelectedUserErrorStatus(1, "User ID is not valid");
        }

        UserDTO userDTO = user.getUser(userId);
        return userDTO;


    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) {
        String regexForUserID = "^UID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);


        try {
            if (!regexMatcher.matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            user.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return user.getUserList();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(@RequestPart("userFirstName") String userFirstName,
                           @RequestPart("userLastName") String userLastName,
                           @RequestPart("userEmail") String userEmail,
                           @RequestPart("userPassword") String userPassword,
                           @RequestPart("profilePicture") MultipartFile profilePicture,
                           @PathVariable("userId") String userId
    ) {
        String base64ProPic = "";

        try {
            byte [] bytesProImg= profilePicture.getBytes();// create byte collection of image meka hdnne ape pahasuwata mok
            //profile picture convert to base64
            // Todo: Build the object

            var buildUserDTO = new UserDTO();
            base64ProPic = AppUtil.convertProfilePictureToBase64(bytesProImg);//apita byte array ekk database eke save krnn beri nisa eka string ekk krl eka database ekt pass krnw
            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(userFirstName);
            buildUserDTO.setLastName(userLastName);
            buildUserDTO.setEmail(userEmail);
            buildUserDTO.setPassword(userPassword);
            buildUserDTO.setProfilePic(base64ProPic);

            user.updateUser(buildUserDTO.getUserId(),buildUserDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
