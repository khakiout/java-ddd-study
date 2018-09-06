package com.khakiout.study.ddddemo.infrastructure.mapper;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.models.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMapper implements BaseRepositoryModelMapper<User, UserEntity> {

    Logger logger = LoggerFactory.getLogger(UserMapper.class);

    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMethodAccessLevel(AccessLevel.PRIVATE);
    }

    @Override
    public UserEntity map(User repositoryModel) throws EntityValidationException {
        logger.debug("Mapping User Repository Model to User Entity.");
        UserEntity entity = new UserEntity(repositoryModel.getId(), repositoryModel.getFirstName(),
            repositoryModel.getLastName(), repositoryModel.getEmail(),
            repositoryModel.getCreatedAt(), repositoryModel.getUpdatedAt());

        return entity;
    }

    @Override
    public User map(UserEntity entity) {
        logger.debug("Mapping User Repository Model to User DTO.");
        User user = modelMapper.map(entity, User.class);

        return user;
    }
}
