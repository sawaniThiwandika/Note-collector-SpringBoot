package lk.ijse.demo.service;



import lk.ijse.demo.dto.impl.UserDTO;

import java.util.List;

public interface UserServise {
    public UserDTO saveUser(UserDTO dto);
    public boolean updateUser(String userID,UserDTO dto);
    public boolean deleteUser(String id);
    public UserDTO getUser(String id);
    public List<UserDTO> getUserList();

}
