package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.validation.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserApplication implements BaseApplication<UserEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    protected final SpringValidatorAdapter validatorAdapter;

    /**
     * Instantiate the user application via DI.
     *
     * @param userRepository the injected user repository.
     * @param validatorAdapter the injected spring validator.
     */
    public UserApplication(UserRepository userRepository, SpringValidatorAdapter validatorAdapter) {
        LOGGER.debug("Starting service.");
        this.validatorAdapter = validatorAdapter;
        this.userRepository = userRepository;
    }

    @Override
    public Flux<UserEntity> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Mono<UserEntity> findById(String id) {
        LOGGER.info("Retrieving entity [{}]", id);
        return userRepository.findById(id);
    }

    @Override
    public Mono<UserEntity> create(UserEntity userEntity) {
        Mono<UserEntity> response;

        try {
            LOGGER.info("Creating user.");
            userEntity.validate();
            response = userRepository.create(userEntity);
        } catch (EntityValidationException eve) {
            LOGGER.error(eve.getMessage());
            response = Mono.error(eve);
        }

        return response;
    }

    @Override
    public Mono<UserEntity> update(String id, UserEntity userEntity) {
        Mono<UserEntity> response;

        try {
            LOGGER.info("Updating user for with id of [{}]", id);
            userEntity.validate();
            response = userRepository.update(id, userEntity);
        } catch (EntityValidationException eve) {
            LOGGER.error(eve.getMessage());
            response = Mono.error(eve);
        }

        return response;
    }

    @Override
    public Mono<Void> delete(String id) {
        LOGGER.info("Deleting [{}]", id);
        return this.findById(id)
            .switchIfEmpty(Mono.error(new EntityNotFoundException()))
            .flatMap(userEntity -> userRepository.delete(id));
    }

    @Override
    public Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

}
