package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.mapper.UserMapper;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;
import com.khakiout.study.ddddemo.infrastructure.spring.SpringUserRepository;
import com.khakiout.study.ddddemo.infrastructure.models.User;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    final SpringUserRepository repository;

    final UserMapper userMapper;

    public UserRepositoryImpl(SpringUserRepository repository) {
        this.repository = repository;
        this.userMapper = new UserMapper();
    }

    @Override
    public List<UserEntity> getAll() {
        Iterable<User> userIterable = repository.findAll();
        List<UserEntity> users = new ArrayList<>();
        userIterable.forEach(user -> {
            try {
                UserEntity userEntity = userMapper.map(user);
                users.add(userEntity);
            } catch (EntityValidationException eve) {
                logger.error("This should not happen", eve);
            }
        });
        logger.info("Found [{}] users", users.size());

        return users;
    }

    @Override
    public UserEntity findById(String id) {
        Long idValue = Long.valueOf(id);

        UserEntity userEntity = null;
        User user = repository.findById(idValue).orElse(null);
        if (user != null) {
            try {
                userEntity = userMapper.map(user);
            } catch (EntityValidationException eve) {
                logger.error("This should not happen", eve);
            }
        }
        return userEntity;
    }

    @Override
    public void create(UserEntity userEntity) {
        logger.info("Creating user");
        User user = userMapper.map(userEntity);
        repository.save(user);
        logger.info("User creation success");
    }

    @Override
    public void update(String id, UserEntity userEntity) {
        logger.info("Modifying user [{}]", id);
        User user = userMapper.map(userEntity);
        if (this.findById(id) != null) {
            repository.save(user);
            logger.info("User modification success");
        } else {
            logger.warn("Failed to update missing user");
        }
    }

    @Override
    public void delete(String id) {

    }

}
