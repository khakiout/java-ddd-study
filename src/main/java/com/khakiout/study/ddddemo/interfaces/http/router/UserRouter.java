package com.khakiout.study.ddddemo.interfaces.http.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import com.khakiout.study.ddddemo.interfaces.http.handler.user.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Routers for Users.
 */
@Component
public class UserRouter {

    /**
     * Describe the routes for the User entity.
     *
     * @param handler the handler to process the route requests.
     * @return the router functions.
     */
    @Bean
    public RouterFunction<ServerResponse> route(UserHandler handler) {

        return RouterFunctions
            .route(
                GET("/users"),
                handler::index)
            .andRoute(
                GET("/users/{id}"),
                handler::show)
            .andRoute(
                POST("/users")
                    .and(contentType(APPLICATION_JSON)),
                handler::create)
            .andRoute(
                PUT("/users/{id}")
                    .and(contentType(APPLICATION_JSON)),
                handler::update)
            .andRoute(
                DELETE("/users/{id}"),
                handler::delete);

    }

}
