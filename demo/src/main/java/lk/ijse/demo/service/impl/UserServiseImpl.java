package lk.ijse.demo.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.demo.dao.UserDao;
import lk.ijse.demo.dto.impl.UserDTO;
import lk.ijse.demo.entity.impl.UserEntity;
import lk.ijse.demo.exception.UserNotFoundException;
import lk.ijse.demo.service.UserServise;
import lk.ijse.demo.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
// meta annotate wela thiynw component annotate eken e nisa api methana @componenet annotaion eka wenama use krnn oni ne
public class UserServiseImpl implements UserServise {
    @Autowired
    private UserDao userDao;

    @Autowired
    private Mapping mapping;
    @Override
    public UserDTO saveUser(UserDTO dto) {
        return mapping.toUserDto( userDao.save(mapping.toUserEntity(dto)));
    }

    @Override
    public boolean updateUser(String userID, UserDTO dto) {

        Optional<UserEntity> tmpUser = userDao.findById(userID);
        if (tmpUser.isPresent()){
            tmpUser.get().setFirstName(dto.getFirstName());
            tmpUser.get().setLastName(dto.getLastName());
            tmpUser.get().setPassword(dto.getPassword());
            tmpUser.get().setEmail(dto.getEmail());
            tmpUser.get().setProfilePic(dto.getProfilePic());

        }
        return true;
    }

    @Override
    public boolean deleteUser(String id) {

        Optional<UserEntity> existUser = userDao.findById(id);
        if (!existUser.isPresent()){
           throw new UserNotFoundException();

        }
        else {
            userDao.deleteById(id);
            return true;
        }


    }

    @Override
    public UserDTO getUser(String id) {
     return mapping.toUserDto(userDao.getReferenceById(id));


    }

    @Override
    public List<UserDTO> getUserList() {
      List<UserEntity> all = userDao.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for (UserEntity entity:all
             ) {
            dtoList.add(mapping.toUserDto(entity));
        }
        return dtoList;
    }

}
