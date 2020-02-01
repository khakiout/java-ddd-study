package com.khakiout.study.ddddemo.interfaces.http.handler;

import com.khakiout.study.ddddemo.app.BaseApplication;
import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import com.khakiout.study.ddddemo.domain.validation.error.ValidationErrorItem;
import com.khakiout.study.ddddemo.domain.validation.exception.EntityValidationException;
import com.khakiout.study.ddddemo.interfaces.http.response.GenericBadRequestResponse;
import com.khakiout.study.ddddemo.interfaces.http.response.NotFoundResponse;
import com.khakiout.study.ddddemo.interfaces.http.response.ValidationErrorResponse;
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

    static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);

    private final BaseApplication<BaseEntity> application;

    /**
     * Constructor for the handler.
     * We added {code}SuppressWarnings(...){/code} to avoid class-cast
     * warnings.
     *
     * @param application the entity application.
     */
    @SuppressWarnings("unchecked")
    protected BaseHandler(final BaseApplication<? extends BaseEntity> application) {
        this.application = (BaseApplication<BaseEntity>) application;
    }

    /**
     * Get the list of the entities.
     *
     * @param request the server request
     * @return the http error with the list of Entities.
     */
    public Mono<ServerResponse> index(final ServerRequest request) {
        LOGGER.info("List entities.");
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
    public Mono<ServerResponse> show(final ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<? extends BaseEntity> entityMono = application.findById(id);

        return entityMono
            .flatMap(entity -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(entityMono,
                    ParameterizedTypeReference.forType(application.getEntityClass())))
            .switchIfEmpty(returnNotFound());
    }

    /**
     * Create an entity.
     *
     * @param request the server request with the Entity details to be created.
     * @return the http error.
     */
    public Mono<ServerResponse> create(final ServerRequest request) {
        return request.bodyToMono(application.getEntityClass())
            .map(entity -> {
                LOGGER.info("Got entity for creation.");
                return this.application.create(entity);
            })
            .flatMap(createdEntity -> {
                LOGGER.info("Done processing entity creation.");
                return createdEntity
                    .flatMap(entity -> {
                        LOGGER.debug("Returning success error");

                        return ServerResponse
                            .created(URI.create(request.uri().toString() + entity.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(createdEntity, application.getEntityClass());
                    })
                    .onErrorResume(EntityValidationException.class, this::returnValidationErrors);
            })
            .switchIfEmpty(returnGenericBadRequest());
    }

    /**
     * Modify an entity.
     *
     * @param request the server request with the Entity details to be updated.
     * @return the http error.
     */
    public Mono<ServerResponse> update(final ServerRequest request) {
        String id = request.pathVariable("id");

        return request.bodyToMono(application.getEntityClass())
            .map(entity -> {
                LOGGER.info("Got entity for modification.");
                return this.application.update(id, entity);
            })
            .flatMap(updatedEntity -> {
                LOGGER.info("Done processing entity modification.");
                return updatedEntity
                    .flatMap(entity -> {
                        LOGGER.info("Updated entity.");

                        return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(updatedEntity, application.getEntityClass());
                    })
                    .switchIfEmpty(returnNotFound())
                    .onErrorResume(EntityValidationException.class, this::returnValidationErrors);
            })
            .switchIfEmpty(returnGenericBadRequest());
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
                LOGGER.debug("Failed to delete non-existent entity.");
                return returnNotFound();
            });
    }

    /**
     * Return the generic 404 response for missing entities.
     *
     * @return the 404 response.
     */
    private Mono<ServerResponse> returnNotFound() {
        return ServerResponse
            .status(HttpStatus.NOT_FOUND)
            .body(Mono.just(new NotFoundResponse()), NotFoundResponse.class);
    }

    /**
     * Return a generic 400 bad request response.
     *
     * @return the 400 response.
     */
    private Mono<ServerResponse> returnGenericBadRequest() {
        return ServerResponse
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(new GenericBadRequestResponse()), GenericBadRequestResponse.class);
    }

    /**
     * Process the thrown EntityValidationException and return a bad request error.
     *
     * @param eve the exception with the validation errors.
     * @return the server response.
     */
    protected Mono<? extends ServerResponse> returnValidationErrors(EntityValidationException eve) {
        LOGGER.info(eve.getMessage());

        ValidationErrorResponse validationReport = new ValidationErrorResponse();
        for (ValidationErrorItem error : eve.getErrorMessages()) {
            validationReport.addError(error);
        }
        return ServerResponse.badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(validationReport), ValidationErrorResponse.class);
    }

}
