package com.khakiout.study.ddddemo.interfaces.http.controller.user;

import com.khakiout.study.ddddemo.app.user.UserApplication;
import com.khakiout.study.ddddemo.domain.entity.UserEntity;
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

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<UserEntity> userEntityMono = userApplication.findById(id);

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userEntityMono, UserEntity.class);
    }

    @Override
    public Mono<ServerResponse> get() {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userApplication.getAll(), UserEntity.class);
    }

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<UserEntity> userEntityMono = request.bodyToMono(UserEntity.class);
        return userEntityMono.subscribe(
            /**
             * TODO
             * research how to handle throwable error on reactive streams properly
             * possible reference: https://stackoverflow.com/questions/42842514/most-proper-way-to-throw-exception-as-validation-for-reactive-stream
             */
            userEntity -> userApplication.create(userEntity), // emit
            throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON), // onError
            () -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userEntityMono, UserEntity.class) // onSuccess
        );
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<UserEntity> userEntityMono = request.bodyToMono(UserEntity.class);


        return userEntityMono.subscribe(
            /**
             * TODO
             * research how to handle throwable error on reactive streams properly
             * possible reference: https://stackoverflow.com/questions/42842514/most-proper-way-to-throw-exception-as-validation-for-reactive-stream
             */
            userEntity -> userApplication.update(id, userEntity).subscribe(), // emit
            throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON), // onError,
            () ->  ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userEntityMono, UserEntity.class) // onSuccess
        );
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> userEntity = userApplication.delete(id);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build(userEntity);
    }
}
