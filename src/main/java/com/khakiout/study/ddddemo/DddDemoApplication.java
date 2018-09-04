package com.khakiout.study.ddddemo;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DddDemoApplication {

    private static UserRepository userRepository;

    public static void main(String[] args) throws Exception {
        ApplicationContext application = SpringApplication.run(DddDemoApplication.class, args);

        userRepository = application.getBean(UserRepository.class);

//		createUser("Larry Guevarra", "lguevarra@gmail.com");
//		createUser("Marc Santos", "msantos@gmail.com");
//		createUser("Truth March", "tmarch@gmail.com");
    }

    private static void createUser(String name, String email) throws EntityValidationException {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        UserEntity user = new UserEntity(null, firstName, lastName, email, null, null);
        userRepository.create(user);
    }
}
