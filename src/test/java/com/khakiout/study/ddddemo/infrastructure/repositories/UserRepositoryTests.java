package com.khakiout.study.ddddemo.infrastructure.repositories;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserCreationWithValidInputMustNotError() throws EntityValidationException {
        createUser("Larry Guevarra", "lguevarra@gmail.com");
    }

    @Test(expected = EntityValidationException.class)
    public void testUserCreationWithNullEmailMustError() throws EntityValidationException {
        createUser("Larry Guevarra", null);
    }

    @Test(expected = EntityValidationException.class)
    public void testUserCreationWithInvalidEmailMustError() throws EntityValidationException {
        createUser("Larry Guevarra", "test");
    }

    private void createUser(String name, String email) throws EntityValidationException {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        UserEntity user = new UserEntity(null, firstName, lastName, email, null, null);
        userRepository.create(user);
    }
}
