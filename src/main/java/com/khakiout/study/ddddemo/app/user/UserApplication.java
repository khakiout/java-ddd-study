package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.infrastructure.Repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserApplication implements BaseApplication<UserDTO> {

    @Autowired
    private final Repository userRepository;

    public UserApplication(Repository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAll() {
        return null;
    }

    @Override
    public UserDTO findById(String id) {
        return null;
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
}
