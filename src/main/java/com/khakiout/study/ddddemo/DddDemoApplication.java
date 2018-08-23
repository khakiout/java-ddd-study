package com.khakiout.study.ddddemo;

import com.khakiout.study.ddddemo.domain.entity.User;
import com.khakiout.study.ddddemo.infrastructure.UserRepository;
import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DddDemoApplication {

	public static void main(String[] args) {
		ApplicationContext application = SpringApplication.run(DddDemoApplication.class, args);

		UserRepository userRepository = application.getBean(UserRepository.class);
		User user = new User();
		user.setFirstName("Larry");
		user.setLastName("Bob");
		userRepository.create(user);
	}

	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}
}
