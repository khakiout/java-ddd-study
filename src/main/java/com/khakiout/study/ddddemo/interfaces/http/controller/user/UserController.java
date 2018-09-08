package com.khakiout.study.ddddemo.interfaces.http.controller.user;

import com.khakiout.study.ddddemo.app.user.UserApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationReport;
import com.khakiout.study.ddddemo.infrastructure.models.User;
import com.khakiout.study.ddddemo.interfaces.http.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserController implements BaseController<UserEntity> {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserApplication userApplication;

    public UserController(UserApplication userApplication) {
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

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userEntityMono, UserEntity.class);
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
                        logger.info("OKK");
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
        Mono<UserEntity> userEntityMono = request.bodyToMono(UserEntity.class);

//        return userEntityMono.subscribe(
//            /**
//             * TODO
//             * research how to handle throwable error on reactive streams properly
//             * possible reference: https://stackoverflow.com/questions/42842514/most-proper-way-to-throw-exception-as-validation-for-reactive-stream
//             */
//            userEntity -> userApplication.update(id, userEntity).subscribe(), // emit
//            throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_JSON), // onError,
//            () ->  ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(userEntityMono, UserEntity.class) // onSuccess
//        );

        return Mono.empty();
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> userEntity = userApplication.delete(id);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build(userEntity);
    }
}
