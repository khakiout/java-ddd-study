package com.khakiout.study.ddddemo;

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
	}

}
