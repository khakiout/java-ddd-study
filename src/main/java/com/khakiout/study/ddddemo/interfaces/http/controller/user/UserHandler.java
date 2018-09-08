package com.khakiout.study.ddddemo.interfaces.http.controller.user;

import com.khakiout.study.ddddemo.app.user.UserApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationReport;
import com.khakiout.study.ddddemo.interfaces.http.controller.BaseHandler;
import java.net.URI;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler implements BaseHandler {

    Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private final UserApplication userApplication;

    public UserHandler(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    @Override
    public Mono<ServerResponse> index(ServerRequest request) {

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userApplication.getAll(), UserEntity.class);
    }

    @Override
    public Mono<ServerResponse> show(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<UserEntity> userEntityMono = userApplication.findById(id);

        return userEntityMono
            .flatMap(userEntity -> {
                return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userEntityMono, UserEntity.class);
            })
            .switchIfEmpty(ServerResponse.notFound().build())
            .onErrorResume(error -> {
                logger.error(error.getMessage());
                return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON).build();
            });
    }

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {

        return request.bodyToMono(UserEntity.class)
            .map(userEntity -> {
                logger.info("Got user entity for creation.");
                return this.userApplication.create(userEntity);
            })
            .flatMap(createdUser -> {
                logger.info("Done processing user creation.");
                return createdUser
                    .flatMap(userEntity -> {
                        logger.info("OK");
                        return ServerResponse.created(URI.create("/users/" + userEntity.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(createdUser, UserEntity.class);
                    })
                    .onErrorResume(EntityValidationException.class, eve -> {
                        logger.error(eve.getMessage());
                        return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(eve.getErrorMessages()), ValidationReport.class);
                    })
                    .onErrorResume(error -> {
                        logger.error(error.getMessage());
                        return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON).build();
                    });
            });
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(UserEntity.class)
            .map(userEntity -> {
                logger.info("Got user entity for modification.");
                return this.userApplication.update(id, userEntity);
            })
            .flatMap(createdUser -> {
                logger.info("Done processing user creation.");
                return createdUser
                    .flatMap(userEntity -> {
                        logger.info("Updated user!");
                        return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(createdUser, UserEntity.class);
                    })
                    .onErrorResume(EntityValidationException.class, eve -> {
                        logger.error(eve.getMessage());
                        return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(eve.getErrorMessages()), ValidationReport.class);
                    })
                    .onErrorResume(DataAccessResourceFailureException.class, drfe -> {
                        logger.error("Unauthorized modification");
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just("Can't modify what's not yours."), String.class);
                    })
                    .onErrorResume(error -> {
                        logger.error(error.getMessage());
                        return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON).build();
                    });
            });
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deleteUserAction = userApplication.delete(id);

        return deleteUserAction
            .flatMap(action -> ServerResponse.ok().build())
            .onErrorResume(EntityNotFoundException.class, enfe -> {
                return ServerResponse.status(HttpStatus.NOT_FOUND)
                    .body(Mono.just("Nothing here"), String.class);
            });

    }
}
