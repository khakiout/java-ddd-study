package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO: add reactive
 */
@Service
public class UserApplication implements BaseApplication<UserDTO> {

    Logger logger = LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    private final UserRepository userRepository;

    private final UserDTOMapper userMapper;

    public UserApplication(UserRepository userRepository) {
        logger.debug("Starting service.");
        this.userRepository = userRepository;
        this.userMapper = new UserDTOMapper();
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserEntity> users = userRepository.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> {
            UserDTO dto = userMapper.map(user);

            userDTOs.add(dto);
        });

        return userDTOs;
    }

    @Override
    public UserDTO findById(String id) {
        UserEntity user = userRepository.findById(id);
        UserDTO dto = null;
        if (user != null) {
            dto = userMapper.map(user);
        }

        return dto;
    }

    @Override
    public void create(UserDTO userDTO) throws EntityValidationException {
        UserEntity user = userMapper.map(userDTO);
        userRepository.create(user);
    }

    @Override
    public void update(String id, UserDTO userDTO) {

    }

    @Override
    public void delete(String id) {

    }

}
