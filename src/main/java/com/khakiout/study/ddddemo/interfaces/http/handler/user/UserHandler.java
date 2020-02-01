package com.khakiout.study.ddddemo.interfaces.http.handler.user;

import com.khakiout.study.ddddemo.app.user.UserApplication;
import com.khakiout.study.ddddemo.interfaces.http.handler.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler extends BaseHandler {

    @Autowired
    private final UserApplication userApplication;

    public UserHandler(UserApplication userApplication) {
        super(userApplication);
        this.userApplication = userApplication;
    }

    @Override
    public Mono<ServerResponse> index(ServerRequest request) {
        return super.index(request);
    }

    @Override
    public Mono<ServerResponse> show(ServerRequest request) {
        return super.show(request);
    }

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        return super.create(request);
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        return super.update(request);
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        return super.delete(request);
    }
}
