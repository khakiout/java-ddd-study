package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.User;
import com.khakiout.study.ddddemo.infrastructure.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplication implements BaseApplication<UserDTO> {

    Logger logger = LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    private final UserRepository userRepository;

    public UserApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> {
            UserDTO dto = this.mapDTO(user);

            userDTOs.add(dto);
        });

        return userDTOs;
    }

    @Override
    public UserDTO findById(String id) {
        User user = userRepository.findById(id);
        UserDTO dto = this.mapDTO(user);

        return dto;
    }

    @Override
    public void create(UserDTO userDTO) {

    }

    @Override
    public void update(String id, UserDTO userDTO) {

    }

    @Override
    public void delete(String id) {

    }

    // TODO: move to a mapper class
    private UserDTO mapDTO(User user) {
        UserDTO dto = new UserDTO();

        if (user != null) {
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
        }

        return dto;
    }
}