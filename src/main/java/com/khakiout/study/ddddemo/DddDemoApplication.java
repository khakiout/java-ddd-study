package com.khakiout.study.ddddemo;

import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;
import com.khakiout.study.ddddemo.infrastructure.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DddDemoApplication {

	private static UserRepository userRepository;

	public static void main(String[] args) {
		ApplicationContext application = SpringApplication.run(DddDemoApplication.class, args);

		userRepository = application.getBean(UserRepository.class);

		createUser("Larry Guevarra", "lguevarra@gmail.com");
		createUser("Marc Santos", "msantos@gmail.com");
		createUser("Truth March", "tmarch@gmail.com");
	}

	private static void createUser(String name, String email) {
		UserEntity user = new UserEntity();
		user.setFirstName(name.split(" ")[0]);
		user.setLastName(name.split(" ")[1]);
		user.setEmail(new EmailValueObject(email));
		userRepository.create(user);
	}
}
