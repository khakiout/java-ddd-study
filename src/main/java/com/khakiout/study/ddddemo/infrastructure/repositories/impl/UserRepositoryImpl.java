package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;
import com.khakiout.study.ddddemo.infrastructure.models.User;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;
import com.khakiout.study.ddddemo.infrastructure.spring.SpringUserRepository;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    @Autowired
    final SpringUserRepository repository;

    public UserRepositoryImpl(SpringUserRepository repository, SpringValidatorAdapter validator) {
        super(validator);
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
        LOGGER.info("Found [{}] users", users.size());

        return Flux.fromIterable(users);
    }

    @Override
    public Mono<UserEntity> findById(String id) {
        Long idValue = Long.valueOf(id);

        UserEntity userEntity = null;
        Optional<User> user = repository.findById(idValue);
        if (user.isPresent()) {
            userEntity = transform(user.get());
        }

        return Mono.justOrEmpty(userEntity);
    }

    @Override
    public Mono<UserEntity> create(UserEntity userEntity) {
        LOGGER.info("Creating user");

        User user = this.transform(userEntity);
        user.setId(null); // ensure that this is a new entry.

        this.validate(user);
        User created = repository.save(user);
        LOGGER.info("User creation success");

        return Mono.just(transform(created));
    }

    @Override
    public Mono<UserEntity> update(String id, UserEntity userEntity) {
        LOGGER.info("Modifying user [{}]", id);

        return this.findById(id)
            .flatMap(userInDB -> {
                LOGGER.info("Found user {}", userInDB.getId());
                User user = this.transform(userEntity);
                user.setId(Long.valueOf(id));
                user.setCreatedAt(userInDB.getCreatedAt());

                this.validate(user);
                User updated = repository.save(user);
                LOGGER.info("User modification success");

                return Mono.just(transform(updated));
            });

    }

    @Override
    public Mono<Void> delete(String id) {
        LOGGER.info("Deleting user [{}]", id);
        repository.deleteById(Long.valueOf(id));
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
        return new UserEntity(user.getId(), user.getFirstName(),
            user.getLastName(),
            user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
