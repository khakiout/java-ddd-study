package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseDTOMapper;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converter for User DTO to User entity and vice versa.
 */
public class UserDTOMapper implements BaseDTOMapper<UserDTO, UserEntity> {

    Logger logger = LoggerFactory.getLogger(UserDTOMapper.class);

    private final ModelMapper modelMapper;

    public UserDTOMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMethodAccessLevel(AccessLevel.PRIVATE);
    }

    @Override
    public UserEntity map(UserDTO dto) throws EntityValidationException {
        logger.debug("Mapping User DTO to User Entity.");
        UserEntity entity = new UserEntity(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail());

        return entity;
    }

    @Override
    public UserDTO map(UserEntity entity) {
        logger.debug("Mapping User Entity to User DTO.");
        UserDTO dto = modelMapper.map(entity, UserDTO.class);

        return dto;
    }
}
