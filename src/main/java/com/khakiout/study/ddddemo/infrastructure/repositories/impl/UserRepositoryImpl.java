package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserRepositoryImpl implements UserRepository {

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    final SpringUserRepository repository;

    public UserRepositoryImpl(SpringUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<UserEntity> getAll() {
        Iterable<User> userIterable = repository.findAll();
        List<UserEntity> users = new ArrayList<>();
        userIterable.forEach(user -> {
            UserEntity entity = transform(user);
            users.add(entity);
        });
        logger.info("Found [{}] users", users.size());

        return Flux.fromIterable(users);
    }

    @Override
    public Mono<UserEntity> findById(String id) {
        Long idValue = Long.valueOf(id);

        UserEntity userEntity = null;
        User user = repository.findById(idValue).orElse(null);
        if (user != null) {
            userEntity = transform(user);
        }

        return Mono.just(userEntity);
    }

    @Override
    public Mono<UserEntity> create(UserEntity userEntity) {
        logger.info("Creating user");

        User user = this.transform(userEntity);

        User created = repository.save(user);
        logger.info("User creation success");

        return Mono.just(transform(created));
    }

    @Override
    public Mono<UserEntity> update(String id, UserEntity userEntity) {
        logger.info("Modifying user [{}]", id);
        User user = this.transform(userEntity);
        if (this.findById(id) != null) {
            User created = repository.save(user);
            logger.info("User modification success");

            return Mono.just(transform(created));
        } else {
            logger.warn("Failed to update missing user");
            return Mono.empty();
        }
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.empty();
    }

    // TODO: move this to transformer class

    /**
     * Transform entity.
     *
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
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        return user;
    }

    /**
     * Transform entity.
     *
     * @return the user
     */
    private UserEntity transform(User user) {
       UserEntity userEntity = new UserEntity(user.getId(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());

        return userEntity;
    }
}
