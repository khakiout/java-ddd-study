package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.domain.entity.User;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;
import com.khakiout.study.ddddemo.infrastructure.spring.SpringUserRepository;
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

    public UserRepositoryImpl(SpringUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        Iterable<User> userIterable = repository.findAll();
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        logger.info("Found [{}] users", users.size());
        return users;
    }

    @Override
    public User findById(String id) {
        Long idValue = Long.valueOf(id);
        return repository.findById(idValue).orElse(null);
    }

    @Override
    public void create(User user) {
        logger.info("Creating user");
        repository.save(user);
        logger.info("User creation success");
    }

    @Override
    public void update(String id, User user) {

    }

    @Override
    public void delete(String id) {

    }
}
