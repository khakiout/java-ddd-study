package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;
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

    public UserRepositoryImpl(SpringUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserEntity> getAll() {
        Iterable<User> userIterable = repository.findAll();
        List<UserEntity> users = new ArrayList<>();
        userIterable.forEach(user -> {
            UserEntity entity = transform(user);
            users.add(entity);
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
            userEntity = transform(user);
        }
        return userEntity;
    }

    @Override
    public void create(UserEntity userEntity) {
        logger.info("Creating user");
        User user = this.transform(userEntity);
        repository.save(user);
        logger.info("User creation success");
    }

    @Override
    public void update(String id, UserEntity userEntity) {
        logger.info("Modifying user [{}]", id);
        User user = this.transform(userEntity);
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

    // TODO: move this to transformer class
    /**
     * Transform entity.
     *
     * @param entity
     * @return the user
     */
    private User transform(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        EmailValueObject entityEmail = entity.getEmail();
        // TODO: remove null check
        if (entityEmail != null) {
            user.setEmail(entityEmail.getEmail());
        }

        return user;
    }

    /**
     * Transform entity.
     *
     * @param user
     * @return the user
     */
    private UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(),
            user.getEmail(), null, null);

        return userEntity;
    }
}
