package com.khakiout.study.ddddemo.interfaces.http.handler;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import com.khakiout.study.ddddemo.domain.validation.error.ValidationErrorItem;
import com.khakiout.study.ddddemo.domain.validation.exception.EntityValidationException;
import com.khakiout.study.ddddemo.interfaces.http.response.ValidationReport;
import java.net.URI;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Base for a Route handler for generic Entity.
 *
 * @see com.khakiout.study.ddddemo.domain.entity.BaseEntity
 */
public abstract class BaseHandler {

    private Logger logger = LoggerFactory.getLogger(BaseHandler.class);

    private final BaseApplication<BaseEntity> application;

    /**
     * Constructor for the handler. We added {code}SuppressWarnings(...){/code} to handle class-cast
     * warnings.
     *
     * @param application the entity application.
     */
    @SuppressWarnings("unchecked")
    protected BaseHandler(BaseApplication<? extends BaseEntity> application) {
        this.application = (BaseApplication<BaseEntity>) application;
    }

    /**
     * Get the list of the entities.
     *
     * @param request the server request
     * @return the http error with the list of Entities.
     */
    public Mono<ServerResponse> index(ServerRequest request) {
        logger.info("List entities.");
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(application.getAll(), application.getEntityClass());
    }

    /**
     * Get entity by its ID.
     *
     * @param request the server request with the Entity ID as request param.
     * @return the http error with the entity representation.
     */
    public Mono<ServerResponse> show(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<? extends BaseEntity> entityMono = application.findById(id);

        return entityMono
            .flatMap(entity -> {
                return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(entityMono,
                        ParameterizedTypeReference.forType(application.getEntityClass()));

            })
            .switchIfEmpty(ServerResponse.notFound().build())
            .onErrorResume(error -> {
                logger.error(error.getMessage());
                return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON).build();
            });
    }

    /**
     * Create an entity.
     *
     * @param request the server request with the Entity details to be created.
     * @return the http error.
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(application.getEntityClass())
            .map(entity -> {
                logger.info("Got entity for creation.");
                return this.application.create(entity);
            })
            .flatMap(createdEntity -> {
                logger.info("Done processing entity creation.");
                return createdEntity
                    .flatMap(entity -> {
                        logger.debug("Returning success error");
                        return ServerResponse
                            .created(URI.create(request.uri().toString() + entity.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(createdEntity, application.getEntityClass());
                    })
                    .onErrorResume(EntityValidationException.class, eve -> {
                        logger.error(eve.getMessage());
                        ValidationReport validationReport = new ValidationReport();
                        for (ValidationErrorItem error : eve.getErrorMessages()) {
                            validationReport.addError(error);
                        }
                        return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(validationReport), ValidationReport.class);
                    })
                    .onErrorResume(error -> {
                        logger.error(error.getMessage());
                        return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON).build();
                    });
            })
            .switchIfEmpty(ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .build()
            );
    }

    /**
     * Modify an entity.
     *
     * @param request the server request with the Entity details to be updated.
     * @return the http error.
     */
    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(application.getEntityClass())
            .map(entity -> {
                logger.info("Got entity for modification.");
                return this.application.update(id, entity);
            })
            .flatMap(updatedEntity -> {
                logger.info("Done processing entity modification.");
                return updatedEntity
                    .flatMap(entity -> {
                        logger.info("Updated entity.");
                        return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(updatedEntity, application.getEntityClass());
                    })
                    .switchIfEmpty(ServerResponse.notFound().build())
                    .onErrorResume(EntityValidationException.class, eve -> {
                        logger.error(eve.getMessage());
                        ValidationReport validationReport = new ValidationReport();
                        for (ValidationErrorItem error : eve.getErrorMessages()) {
                            validationReport.addError(error);
                        }
                        return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(validationReport), ValidationReport.class);
                    })
                    .onErrorResume(error -> {
                        logger.error(error.getMessage());
                        return ServerResponse
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON).build();
                    });
            })
            .switchIfEmpty(ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .build()
            );
    }

    /**
     * Delete an entity.
     *
     * @param request the server request with Entity ID as route entry.
     * @return the http error
     */
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deleteEntityAction = application.delete(id);

        return deleteEntityAction
            .flatMap(action -> ServerResponse.ok().build())
            .onErrorResume(EntityNotFoundException.class, enfe -> {
                logger.debug("Failed to delete non-existent entity.");
                return ServerResponse.status(HttpStatus.NOT_FOUND).build();
            });
    }

}
