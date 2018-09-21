package com.khakiout.study.ddddemo.domain.entity;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationErrorItem;
import java.util.List;
import org.junit.Test;

public class UserEntityTest {

    @Test
    public void testValidEntityMustNotError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "lobat@gmail.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNull(eve);
    }

    @Test
    public void testInvalidFirstNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "", "Batista", "lobat@gmail.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        listErrors(eve.getErrorMessages());
        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertFalse(eve.getErrorMessages().isEmpty());
    }

    @Test
    public void testInvalidLastNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", null, "lobat@gmail.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        listErrors(eve.getErrorMessages());
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertFalse(eve.getErrorMessages().isEmpty());
    }

    @Test
    public void testEmptyLastNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "", "lobat@gmail.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        listErrors(eve.getErrorMessages());
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertFalse(eve.getErrorMessages().isEmpty());
    }

    @Test
    public void testInvalidEmailOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "l.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        listErrors(eve.getErrorMessages());
        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().size());
    }

    @Test
    public void testMultipleInvalidFieldsOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(null, null, null, "l.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        List<ValidationErrorItem> errors = eve.getErrorMessages();
        listErrors(errors);
        assertFalse(errors.isEmpty());
        assertTrue(errors.size() >= 3);
        System.out.println(eve.getErrorMessages());
    }

    private void listErrors(List<ValidationErrorItem> errors) {
        for (ValidationErrorItem error : errors) {
            System.out.println(error.getMessage() + " - " + error.getPath());
        }
    }
}
