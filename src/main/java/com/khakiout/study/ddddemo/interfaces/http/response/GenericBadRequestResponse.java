package com.khakiout.study.ddddemo.interfaces.http.response;

/**
 * Generic bad request response.
 */
public class GenericBadRequestResponse extends BaseResponse {

    public final String details = "Bad request.";

    public GenericBadRequestResponse() {
        super("BadRequest");
    }

}
