package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserApplication implements BaseApplication<UserEntity> {

  Logger logger = LoggerFactory.getLogger(UserApplication.class);

  @Autowired
  private final UserRepository userRepository;

  public UserApplication(UserRepository userRepository) {
    logger.debug("Starting service.");
    this.userRepository = userRepository;
  }

  @Override
  public Flux<UserEntity> getAll() {
    return userRepository.getAll();
  }

  @Override
  public Mono<UserEntity> findById(String id) {
    logger.info("Retrieving entity [{}]", id);
    return userRepository.findById(id);
  }

  @Override
  public Mono<UserEntity> create(UserEntity userEntity) {
    try {
      logger.info("Creating user.");
      userEntity.validate();
      return userRepository.create(userEntity);
    } catch (EntityValidationException eve) {
      logger.error(eve.getMessage());
      return Mono.error(eve);
    }
  }

  @Override
  public Mono<UserEntity> update(String id, UserEntity userEntity) {
    try {
      logger.info("Updating user for with id of [{}]", id);
      userEntity.validate();
      return userRepository.update(id, userEntity);
    } catch (EntityValidationException eve) {
      logger.error(eve.getMessage());
      return Mono.error(eve);
    }
  }

  @Override
  public Mono<Void> delete(String id) {
    logger.info("Deleting [{}]", id);
    return this.findById(id)
        .switchIfEmpty(Mono.error(new EntityNotFoundException()))
        .flatMap(userEntity -> userRepository.delete(id));
  }

  @Override
  public Class<UserEntity> getEntityClass() {
    return UserEntity.class;
  }

}
