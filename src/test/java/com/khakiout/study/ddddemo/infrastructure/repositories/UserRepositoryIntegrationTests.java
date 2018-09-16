package com.khakiout.study.ddddemo.infrastructure.repositories;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTests {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testUserCreationWithValidInputMustNotError() {
    createUser("Larry Guevarra", "lguevarra@gmail.com");
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void testUserCreationWithNullEmailMustError() {
    createUser("Larry Guevarra", null);
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void testUserCreationWithEmptyEmailMustError() {
    createUser("Larry Guevarra", "");
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void testUserCreationWithInvalidEmailMustError() {
    createUser("Larry Guevarra", "dasdasdasda");
  }

  private void createUser(String name, String email) {
    String firstName = name.split(" ")[0];
    String lastName = name.split(" ")[1];
    UserEntity user = new UserEntity(null, firstName, lastName, email, null, null);
    userRepository.create(user);
  }
}
