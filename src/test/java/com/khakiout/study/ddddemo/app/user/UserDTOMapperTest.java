package com.khakiout.study.ddddemo.app.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import org.junit.Test;

public class UserDTOMapperTest {

    private final UserDTOMapper mapper;

    public UserDTOMapperTest() {
        this.mapper = new UserDTOMapper();
    }

    @Test
    public void testDtoShouldBeConvertedToEntity() throws EntityValidationException {
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setEmail("mcart@gmail.com");
        dto.setFirstName("Mario");
        dto.setLastName("Cart");

        UserEntity entity = mapper.map(dto);

        assertNotNull(entity);
        assertEquals(Long.valueOf(1L), entity.getId());
        assertEquals("Mario", entity.getFirstName());
        assertEquals("Cart", entity.getLastName());
        assertEquals("mcart@gmail.com", entity.getEmail().getEmail());
    }

    @Test
    public void testEntityShouldBeConvertedToDTO() throws EntityValidationException {
        UserEntity entity = new UserEntity(1L, "Mario", "Cart", "mcart@gmai.com");

        UserDTO dto = mapper.map(entity);

        assertNotNull(dto);
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals("Mario", dto.getFirstName());
        assertEquals("Cart", dto.getLastName());
        assertEquals("mcart@gmai.com", dto.getEmail());
    }
}
