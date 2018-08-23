package com.khakiout.study.ddddemo.interfaces.http.controller;

import com.khakiout.study.ddddemo.app.BaseDTO;

/**
 * Base for an HTTP controller.
 */
public interface BaseController<T extends BaseDTO> {

    T getById();

}
