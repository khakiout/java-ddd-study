package com.khakiout.study.ddddemo.domain.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration of the possible actions to be performed by users.
 */
public enum Permission implements GrantedAuthority {

    VIEW_USERS,
    CREATE_USERS,
    EDIT_USERS,
    DELETE_USERS;

    @Override
    public String getAuthority() {
        return this.name();
    }

}
