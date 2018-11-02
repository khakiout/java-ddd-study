package com.khakiout.study.ddddemo.app.config.security;

import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Date service for creating helper methods for dates.
 */
@Component
public class DateService {

    /**
     * Instantiate the current date.
     *
     * @return the current date.
     */
    public Date newDate() {
        return new Date();
    }

}
